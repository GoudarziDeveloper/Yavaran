package com.tinyDeveloper.yavaran.publicClass

import android.graphics.Bitmap
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.users.FindAndGetUserModel
import com.tinyDeveloper.yavaran.api.apiModels.users.UpdateAndDeleteUserModel
import com.tinyDeveloper.yavaran.api.apiModels.users.UserAllModel
import com.tinyDeveloper.yavaran.api.apiModels.users.UserIdAndPhoneModel
import com.tinyDeveloper.yavaran.api.presenters.users.FindAndGetUsersPresenter
import com.tinyDeveloper.yavaran.api.presenters.users.UpdateUserPresenter
import com.tinyDeveloper.yavaran.databinding.AccountFragmentBinding
import com.tinyDeveloper.yavaran.fragments.NeededFragment
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.io.ByteArrayOutputStream

class AccountUpdate: KoinComponent {
    fun find(
        bindAccount: AccountFragmentBinding,
        info: UserIdAndPhoneModel
    ){
        FindAndGetUsersPresenter(object : ValidateDataListener<FindAndGetUserModel> {
            override fun onSuccess(data: FindAndGetUserModel) {
                if (data.status && data.user != null){
                    bindAccount.accountPhone.setText(data.user.phone)
                    bindAccount.accountFirstName.setText(data.user.firstName)
                    bindAccount.accountLastName.setText(data.user.lastName)
                    bindAccount.accountEmail.setText(data.user.email)
                    bindAccount.accountNationalCode.setText(data.user.nationalCode)
                    bindAccount.accountPostalCode.setText(data.user.postalCode)
                    bindAccount.accountState.setText(data.user.state)
                    bindAccount.accountCity.setText(data.user.city)
                    bindAccount.accountAdress.setText(data.user.address)
                    val picassoCreator: PicassoCreator by inject()
                    picassoCreator.getImage(
                         MainActivity.mContext.baseDownloadUrl + data.user.image,null, bindAccount.accountImage)

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
                        var phone = MainActivity.mContext.phone
                        if (phone != null){
                            update(
                                bindAccount,
                                UserAllModel(
                                    "update",
                                    data.user.id.toString(),
                                    phone.toString(),
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
                                    data.user.neededLevel,
                                    System.currentTimeMillis().toString(),
                                    data.user.status
                                )
                            )
                        }
                    }
                }
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate)
                    Toast.makeText(MainActivity.mContext, "noValid", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Throwable) {
            }
        })   .findOrGetUser(info)
    }

    fun update(
        bindAccount:AccountFragmentBinding,
        info: UserAllModel
    ){
        UpdateUserPresenter(object : ValidateDataListener<UpdateAndDeleteUserModel> {
            override fun onSuccess(data: UpdateAndDeleteUserModel) {
                if (data.success){
                    Toast.makeText(MainActivity.mContext, "بروزرسانی موفق بود", Toast.LENGTH_SHORT).show()
                    MainActivity.mContext.supportFragmentManager.popBackStack()
                }

                else
                    Toast.makeText(MainActivity.mContext, "ناموفق", Toast.LENGTH_SHORT).show()
            }

            override fun onInvalidate(isValidate: Boolean) {
                bindAccount.accountBtnSave.isEnabled = true
                if (!isValidate)
                    Toast.makeText(MainActivity.mContext, "خطایی رخ داده است دوباره تلاش کنید", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Throwable) {
                bindAccount.accountBtnSave.isEnabled = true
            }
        }).updateUser(info)
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
}