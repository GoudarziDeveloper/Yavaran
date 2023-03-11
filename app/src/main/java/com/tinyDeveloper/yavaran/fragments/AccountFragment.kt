package com.tinyDeveloper.yavaran.fragments

import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.users.InsertUserModel
import com.tinyDeveloper.yavaran.api.apiModels.users.UserAllModel
import com.tinyDeveloper.yavaran.api.apiModels.users.UserIdAndPhoneModel
import com.tinyDeveloper.yavaran.api.presenters.users.InsertUserPresenter
import com.tinyDeveloper.yavaran.databinding.AccountFragmentBinding
import com.tinyDeveloper.yavaran.publicClass.*
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream

class AccountFragment: Fragment() {
    private var _bindAccount : AccountFragmentBinding? = null
    private val bindAccount get() = _bindAccount!!

    private lateinit var selectDialog: AlertDialog
    private lateinit var selectDialogBuilder: AlertDialog.Builder

    var from : String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindAccount = AccountFragmentBinding.inflate(layoutInflater, container, false)
        return bindAccount.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var phone = MainActivity.mContext.phone
        val type = arguments?.getString("type", null)
        from = arguments?.getString("from", null)
        if (type != null){
            if (type == "insert"){
                bindAccount.accountPhone.isEnabled = true
                bindAccount.accountBtnSave.setOnClickListener {
                    bindAccount.accountBtnSave.isEnabled = false
                    val converter : Converter by inject()
                    var image = ""
                    val def = ContextCompat.getDrawable(MainActivity.mContext, R.drawable.user_image)?.toBitmap()
                    val newImage = bindAccount.accountImage.drawable.toBitmap()
                    if (!newImage.sameAs(def)){
                        image = "data:image/png;base64," +
                                encodeToBase64(
                                    converter.drawableToBitmap(bindAccount.accountImage.drawable))
                    }
                    if (phone != null){
                        insert(
                            bindAccount,
                            UserAllModel(
                                "insert",
                                "",
                                phone,
                                bindAccount.accountFirstName.text.toString(),
                                bindAccount.accountLastName.text.toString(),
                                bindAccount.accountEmail.text.toString(),
                                bindAccount.accountPhone.text.toString(),
                                bindAccount.accountNationalCode.text.toString(),
                                bindAccount.accountAdress.text.toString(),
                                "",
                                "",
                                bindAccount.accountPostalCode.text.toString(),
                                bindAccount.accountState.text.toString(),
                                bindAccount.accountCity.text.toString(),
                                image,
                                "hold",
                                System.currentTimeMillis().toString(),
                                "child",
                            )
                        )
                    }
                }
            }else if (type == "update"){
                if (phone != null){
                    val accountUpdate:AccountUpdate by inject()
                    accountUpdate.find(bindAccount, UserIdAndPhoneModel("find", phone))
                }
            }else if (type == "updateOther"){
                phone = arguments?.getString("mobile", null)
                if (phone != null){
                    val accountUpdate:AccountUpdate by inject()
                    accountUpdate.find(bindAccount, UserIdAndPhoneModel("find", phone))
                }
            }
        }

        bindAccount.accountImage.setOnClickListener {
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.select_image, null)
            val camera = view.findViewById<ImageView>(R.id.selectImageCamera)
            val gallery = view.findViewById<ImageView>(R.id.selectImageGallery)
            selectDialogBuilder = AlertDialog.Builder(requireActivity(), R.style.dialogMain).setView(view)
            selectDialog = selectDialogBuilder.create()
            selectDialog.show()
            camera.setOnClickListener {
                val camera : Camera by inject()
                camera.openCamera(MainActivity.mContext.ACCOUNT_IMAGE_REQUEST_CODE)
                selectDialog.dismiss()
            }
            gallery.setOnClickListener {
                val gallery : Gallery by inject()
                gallery.pickImage(MainActivity.mContext.ACCOUNT_IMAGE_REQUEST_CODE)
                selectDialog.dismiss()
            }
        }

        bindAccount.accountBtnCancel.setOnClickListener {
            MainActivity.mContext.supportFragmentManager.popBackStack()
            MainActivity.mContext.binding.bottomNavigationViewMain.menu.getItem(0).isChecked = true
        }
    }

    fun insert(
        bindAccount:AccountFragmentBinding,
        info: UserAllModel
    ){
        InsertUserPresenter(object : ValidateDataListener<InsertUserModel> {
            override fun onSuccess(data: InsertUserModel) {
                bindAccount.accountBtnSave.isEnabled = true
                if (data.status){
                    Toast.makeText(MainActivity.mContext, "نیازمند جدید افزوده شد!", Toast.LENGTH_SHORT).show()
                    MainActivity.mContext.supportFragmentManager.popBackStack()
                    val public: ChangeFragment by inject()
                    public.newArgumentsFragment(
                        NeededFragment(),
                        isReplace = true,
                        title = "نیازمندان"
                    )
                }
                else
                    Toast.makeText(MainActivity.mContext, "دوباره تلاش کنید", Toast.LENGTH_SHORT).show()
            }

            override fun onInvalidate(isValidate: Boolean) {
                bindAccount.accountBtnSave.isEnabled = true
                if (!isValidate)
                    Toast.makeText(MainActivity.mContext, "خطایی رخ داده است دوباره تلاش کنید", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Throwable) {
                bindAccount.accountBtnSave.isEnabled = true
            }
        }).insertNewUser(info)
    }

    fun encodeToBase64(image: Bitmap?): String? {
        if (image != null){
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b: ByteArray = baos.toByteArray()
            return Base64.encodeToString(b, Base64.DEFAULT)
        }
        return null
    }

    override fun onDestroy() {
        if (from == "needed"){
            MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.INVISIBLE
            MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = "نیازمندان"
        }else if (from == "home"){
            MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.INVISIBLE
            MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = "وضعیت من"
        }

        super.onDestroy()
    }
}