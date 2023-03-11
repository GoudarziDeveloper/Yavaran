package com.tinyDeveloper.yavaran.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tinyDeveloper.yavaran.databinding.RegisterUserFragmentBinding
import org.koin.android.ext.android.inject
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.api.apiModels.users.UserAllModel
import com.tinyDeveloper.yavaran.publicClass.Camera
import com.tinyDeveloper.yavaran.publicClass.Converter
import com.tinyDeveloper.yavaran.publicClass.Gallery
import com.tinyDeveloper.yavaran.publicClass.RegisterUser

class RegisterUserFragment: Fragment() {
    private var _bindRegisterUser: RegisterUserFragmentBinding? = null
    private val bindRegisterUser get() = _bindRegisterUser!!

    private lateinit var selectDialog:AlertDialog
    private lateinit var selectDialogBuilder:AlertDialog.Builder
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindRegisterUser = RegisterUserFragmentBinding.inflate(inflater, container, false)
        return bindRegisterUser.root
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val converter: Converter by inject()
        bindRegisterUser.circleImageView.setOnClickListener {
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.select_image, null)
            val camera = view.findViewById<ImageView>(R.id.selectImageCamera)
            val gallery = view.findViewById<ImageView>(R.id.selectImageGallery)
            selectDialogBuilder = AlertDialog.Builder(requireActivity(), R.style.dialogMain).setView(view)
            selectDialog = selectDialogBuilder.create()
            selectDialog.show()
            camera.setOnClickListener {
                val camera : Camera by inject()
                camera.openCamera(MainActivity.mContext.REGISTER_IMAGE_REQUEST_CODE)
                selectDialog.dismiss()
            }
            gallery.setOnClickListener {
                val gallery : Gallery by inject()
                gallery.pickImage(MainActivity.mContext.REGISTER_IMAGE_REQUEST_CODE)
                selectDialog.dismiss()
            }
        }
        bindRegisterUser.RegisterBtnNext.setOnClickListener {
            val registerUser: RegisterUser by inject()
            var image = ""
            val def = ContextCompat.getDrawable(requireContext(), R.drawable.user_image)?.toBitmap()
            val newImage = bindRegisterUser.circleImageView.drawable.toBitmap()
            if (!newImage.sameAs(def)){
                image = "data:image/png;base64," +
                        registerUser.encodeToBase64(
                            converter.drawableToBitmap(bindRegisterUser.circleImageView.drawable))
            }
            var phone = arguments?.getString("mobile")
            if (phone != null){
                registerUser.insert(
                    UserAllModel(
                        "insert",
                        "",
                        null,
                        bindRegisterUser.registerUserFirstName.text.toString(),
                        bindRegisterUser.RegisterUserLastName.text.toString(),
                        "",
                        phone,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        image,
                        "hold",
                        System.currentTimeMillis().toString(),
                        "active"
                    )
                )
            }
        }
    }

}