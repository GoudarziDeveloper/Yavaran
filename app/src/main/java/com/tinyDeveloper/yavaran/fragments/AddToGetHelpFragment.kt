package com.tinyDeveloper.yavaran.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.AddToGetHelpBodyModel
import com.tinyDeveloper.yavaran.databinding.AddToGetHelpFragmentBinding
import com.tinyDeveloper.yavaran.publicClass.*
import org.koin.android.ext.android.inject
import org.koin.ext.checkedStringValue

class AddToGetHelpFragment : Fragment(){
    private var _bindAddToGetHelp : AddToGetHelpFragmentBinding? = null
    private val bindAddToGetHelp get() = _bindAddToGetHelp!!

    private lateinit var selectDialog: AlertDialog
    private lateinit var selectDialogBuilder: AlertDialog.Builder

    private var parentId: String? = null
    private var phone: String? = null
    private var from: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindAddToGetHelp = AddToGetHelpFragmentBinding.inflate(inflater, container, false)
        return bindAddToGetHelp.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var type:String? = null
        if (arguments != null) {
            type = arguments?.getString("type")
            parentId = arguments?.getString("userId", null)
            from = arguments?.getString("from", null)
        }
        if (type == "update"){
            if (parentId != null){
                phone = parentId!!
            }else{
                phone = MainActivity.mContext.phone?: "-1"
            }
            val updateToGetHelp : UpdateToGetHelp by inject()
            val title = requireArguments().getString("title", "")
            val shortDescription = requireArguments().getString("shortDescription", "")
            val description = requireArguments().getString("description", "")
            val category = requireArguments().getString("category", "")
            val imageList = mutableListOf<String>()
            for (counter in 0..5){
                val image = arguments?.getString("image$counter")
                if (image != null)
                    imageList += image
            }
            updateToGetHelp.updateSetInfo(title, shortDescription, description, category, bindAddToGetHelp, imageList)

        }else if (type == "insert"){
            if (parentId != null){
                phone = parentId!!
            }else{
                phone = MainActivity.mContext.phone?: "-1"
            }
            val categoryTitleList = mutableListOf<String>()
            for (item in MainActivity.mContext.categoriesList){
                categoryTitleList += item.title
            }
            val adapter = ArrayAdapter(requireContext(), R.layout.spinner_color_layout, categoryTitleList)
            adapter.setDropDownViewResource(R.layout.spinner_popup_layout)
            bindAddToGetHelp.addToGetHelpCategory.adapter = adapter

        }
        bindAddToGetHelp.addToGetHelpImageAdd.setOnClickListener {
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.select_image, null)
            val camera = view.findViewById<ImageView>(R.id.selectImageCamera)
            val gallery = view.findViewById<ImageView>(R.id.selectImageGallery)
            selectDialogBuilder = AlertDialog.Builder(requireActivity(), R.style.dialogMain).setView(view)
            selectDialog = selectDialogBuilder.create()
            selectDialog.show()
            camera.setOnClickListener {
                val camera : Camera by inject()
                camera.openCamera(MainActivity.mContext.TO_GET_HELP_IMAGE_REQUEST_CODE)
                selectDialog.dismiss()
            }
            gallery.setOnClickListener {
                val gallery : Gallery by inject()
                gallery.pickImage(MainActivity.mContext.TO_GET_HELP_IMAGE_REQUEST_CODE)
                selectDialog.dismiss()
            }
        }

        bindAddToGetHelp.addToGetHelpBtnCancel.setOnClickListener {
            MainActivity.mContext.supportFragmentManager.popBackStack()
        }
        bindAddToGetHelp.addToGetHelpBtnSave.setOnClickListener {
            if (MainActivity.mContext.phone != null){
                val imageConverter: ImageConverter by inject()
                bindAddToGetHelp.addToGetHelpBtnSave.isEnabled = false
                bindAddToGetHelp.addToGetHelpSavePB.visibility = View.VISIBLE
                val secretTitle = requireActivity().findViewById<RadioButton>(bindAddToGetHelp.addToGetHelpSecret.checkedRadioButtonId)
                val urgencyTitle = requireActivity().findViewById<RadioButton>(bindAddToGetHelp.addToGetHelpUrgency.checkedRadioButtonId)
                val imageViews = listOf(
                    bindAddToGetHelp.addToGetHelpImage1,
                    bindAddToGetHelp.addToGetHelpImage2,
                    bindAddToGetHelp.addToGetHelpImage3,
                    bindAddToGetHelp.addToGetHelpImage4,
                    bindAddToGetHelp.addToGetHelpImage5
                )
                if (type == "insert"){
                    val addToGetHelp : AddToGetHelp by inject()
                    addToGetHelp.addNew(
                        AddToGetHelpBodyModel(
                            "insert",
                            "",
                            phone?:"-1",
                            bindAddToGetHelp.addToGetHelpTitle.text.toString(),
                            bindAddToGetHelp.addToGetHelpShortDescreption.text.toString(),
                            bindAddToGetHelp.addToGetHelpDescreption.text.toString(),
                            (bindAddToGetHelp.addToGetHelpCategory.selectedItemId + 1).toString(),
                            secretTitle.text.toString(),
                            urgencyTitle.text.toString(),
                            imageConverter.convertImages(imageViews),
                            System.currentTimeMillis().toString(),
                            "active"
                        ),
                        bindAddToGetHelp
                    )
                }else if (type == "update"){
                    val id = arguments?.getString("id")
                    if (id != null){
                        val updateToGetHelp : UpdateToGetHelp by inject()
                        updateToGetHelp.updateToGetHelp(
                            AddToGetHelpBodyModel(
                                "update",
                                id,
                                phone?:"-1",
                                bindAddToGetHelp.addToGetHelpTitle.text.toString(),
                                bindAddToGetHelp.addToGetHelpShortDescreption.text.toString(),
                                bindAddToGetHelp.addToGetHelpDescreption.text.toString(),
                                (bindAddToGetHelp.addToGetHelpCategory.selectedItemId + 1).toString(),
                                secretTitle.text.toString(),
                                urgencyTitle.text.toString(),
                                imageConverter.convertImages(imageViews),
                                System.currentTimeMillis().toString(),
                                "active"
                            ),
                            bindAddToGetHelp
                        )
                    }
                }
            }
        }
    }
    override fun onDestroy() {
        if (from == "help"){
            MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.INVISIBLE
            MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = "درخواست ها"
        }else if (from != null){
            MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = from
        }
        super.onDestroy()
    }
}