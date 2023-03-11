package com.tinyDeveloper.yavaran.publicClass

import android.view.View
import android.widget.Toast
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.AddToHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.UpdateAndDeleteToHelpModel
import com.tinyDeveloper.yavaran.api.presenters.toHelp.UpdateToHelpPresenter
import com.tinyDeveloper.yavaran.databinding.AddToHelpFragmentBinding
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class UpdateToHelp: KoinComponent {
    fun update(
        bindAddToHelp: AddToHelpFragmentBinding,
        id: String,
        parentId: String,
        title: String,
        shortDescription: String,
        description: String,
        category: String,
        date: String,
        imageList:List<String>
    ){
        bindAddToHelp.addToHelpTitle.setText(title)
        bindAddToHelp.addToHelpShortDescreption.setText(shortDescription)
        bindAddToHelp.addToHelpDescription.setText(description)
        bindAddToHelp.addToHelpCategory.setSelection(category.toInt().minus(1))

        if (imageList.count() > 0){
            val list = listOf(
                bindAddToHelp.addToHelpImage1,
                bindAddToHelp.addToHelpImage2,
                bindAddToHelp.addToHelpImage3,
                bindAddToHelp.addToHelpImage4,
                bindAddToHelp.addToHelpImage5
            )
            val picasso : PicassoCreator by inject()
            for (item in 0 until imageList.count()){
                picasso.getImage(MainActivity.mContext.baseDownloadUrl + imageList[item], list[item], null)
            }
        }

        bindAddToHelp.addToHelpBtnSave.setOnClickListener {
            bindAddToHelp.addToHelpSavePB.visibility = View.VISIBLE

            val imagesList = listOf(
                bindAddToHelp.addToHelpImage1,
                bindAddToHelp.addToHelpImage2,
                bindAddToHelp.addToHelpImage3,
                bindAddToHelp.addToHelpImage4,
                bindAddToHelp.addToHelpImage5,
            )
            val imageConverter: ImageConverter by inject()
            updateInfo(
                AddToHelpBodyModel(
                    "update",
                    id,
                    parentId,
                    bindAddToHelp.addToHelpTitle.text.toString(),
                    bindAddToHelp.addToHelpShortDescreption.text.toString(),
                    bindAddToHelp.addToHelpDescription.text.toString(),
                    (bindAddToHelp.addToHelpCategory.selectedItemId + 1).toString(),
                    date,
                    "hold",
                    imageConverter.convertImages(imagesList)
                ),
                bindAddToHelp
            )
        }

    }

    fun updateInfo(info: AddToHelpBodyModel, bindAddToHelp: AddToHelpFragmentBinding){
        UpdateToHelpPresenter(object : ValidateDataListener<UpdateAndDeleteToHelpModel> {
            override fun onSuccess(data: UpdateAndDeleteToHelpModel) {
                bindAddToHelp.addToHelpSavePB.visibility = View.INVISIBLE
                if (data.success){
                    MainActivity.mContext.supportFragmentManager.popBackStack()
                    Toast.makeText(MainActivity.mContext, "اطلاعات ذخیره شد", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate){
                    Toast.makeText(MainActivity.mContext, "سرور پاسخگو نبود", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(error: Throwable) {
                bindAddToHelp.addToHelpSavePB.visibility = View.INVISIBLE
            }
        }).toHelpData(info)
    }
}