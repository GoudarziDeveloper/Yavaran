package com.tinyDeveloper.yavaran.fragments

import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.AddToHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.UpdateAndDeleteToHelpModel
import com.tinyDeveloper.yavaran.api.presenters.toHelp.UpdateToHelpPresenter
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.databinding.AddToHelpFragmentBinding
import com.tinyDeveloper.yavaran.publicClass.*
import org.koin.android.ext.android.inject
import org.koin.standalone.inject
import java.io.ByteArrayOutputStream

class AddToHelpFragment: Fragment() {
    private var _bindAddToHelp : AddToHelpFragmentBinding? = null
    private val bindAddToHelp get() = _bindAddToHelp!!

    private lateinit var selectDialog: AlertDialog
    private lateinit var selectDialogBuilder: AlertDialog.Builder

    private lateinit var type:String
    private var from:String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindAddToHelp = AddToHelpFragmentBinding.inflate(inflater, container, false)
        return bindAddToHelp.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var phone = MainActivity.mContext.phone
        val categoryTitleList = mutableListOf<String>()
        for (item in MainActivity.mContext.categoriesList){
            categoryTitleList += item.title
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_color_layout, categoryTitleList)
        adapter.setDropDownViewResource(R.layout.spinner_popup_layout)
        bindAddToHelp.addToHelpCategory.adapter = adapter

        type = requireArguments().getString("type", "-1")
        from = requireArguments().getString("from", "-1")
        if (type == "update"){
            val id = requireArguments().getString("id", "")
            val parentId = requireArguments().getString("userId", "")
            val title = requireArguments().getString("title", "")
            val shortDescription = requireArguments().getString("shortDescription", "")
            val description = requireArguments().getString("description", "")
            val category = requireArguments().getString("category", "")
            val date = requireArguments().getString("date", "")

            val imageList = mutableListOf<String>()
            for (counter in 0..5){
                val image = arguments?.getString("image$counter")
                if (image != null)
                    imageList += image
            }
            val updateToHelp: UpdateToHelp by inject()
            updateToHelp.update(
                bindAddToHelp,
                id,
                parentId,
                title,
                shortDescription,
                description,
                category,
                date,
                imageList
            )
        }else if (type == "insert"){
            bindAddToHelp.addToHelpBtnSave.setOnClickListener {
                bindAddToHelp.addToHelpSavePB.visibility = View.VISIBLE
                if (phone != null){
                    val addToHelp: AddToHelp by inject()
                    val imageConverter: ImageConverter by inject()
                    val imagesList = listOf(
                        bindAddToHelp.addToHelpImage1,
                        bindAddToHelp.addToHelpImage2,
                        bindAddToHelp.addToHelpImage3,
                        bindAddToHelp.addToHelpImage4,
                        bindAddToHelp.addToHelpImage5,
                    )
                    addToHelp.addToHelp(
                        AddToHelpBodyModel(
                            "insert",
                            "",
                            phone.toString(),
                            bindAddToHelp.addToHelpTitle.text.toString(),
                            bindAddToHelp.addToHelpShortDescreption.text.toString(),
                            bindAddToHelp.addToHelpDescription.text.toString(),
                            (bindAddToHelp.addToHelpCategory.selectedItemId + 1).toString(),
                            System.currentTimeMillis().toString(),
                            "hold",
                            imageConverter.convertImages(imagesList)
                        ),
                        bindAddToHelp
                    )
                }
            }
        }

        bindAddToHelp.addToHelpImageAdd.setOnClickListener {
            val view = LayoutInflater.from(MainActivity.mContext).inflate(R.layout.select_image, null)
            val camera = view.findViewById<ImageView>(R.id.selectImageCamera)
            val gallery = view.findViewById<ImageView>(R.id.selectImageGallery)
            selectDialogBuilder = AlertDialog.Builder(MainActivity.mContext, R.style.dialogMain).setView(view)
            selectDialog = selectDialogBuilder.create()
            selectDialog.show()
            camera.setOnClickListener {
                val camera : Camera by inject()
                camera.openCamera(MainActivity.mContext.TO_HELP_IMAGE_REQUEST_CODE)
                selectDialog.dismiss()
            }
            gallery.setOnClickListener {
                val gallery : Gallery by inject()
                gallery.pickImage(MainActivity.mContext.TO_HELP_IMAGE_REQUEST_CODE)
                selectDialog.dismiss()
            }
        }

        bindAddToHelp.addToHelpBtnCancel.setOnClickListener {
            MainActivity.mContext.supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroy() {
        if (from == "gives"){
            MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.INVISIBLE
            MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = "اهدایی ها"
        }else if (from == "home"){
            MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = "اهدایی های من"
        }

        super.onDestroy()
    }
}