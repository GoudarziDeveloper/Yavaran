package com.tinyDeveloper.yavaran.publicClass

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.AddToGetHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.UpdateAndDeleteToGetHelpModel
import com.tinyDeveloper.yavaran.api.presenters.toGetHelp.UpdateToGetHelpPresenter
import com.tinyDeveloper.yavaran.databinding.AddToGetHelpFragmentBinding
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class UpdateToGetHelp: KoinComponent {
    fun updateToGetHelp(
        info: AddToGetHelpBodyModel,
        bindAddToGetHelp: AddToGetHelpFragmentBinding
    ){
        UpdateToGetHelpPresenter(object : ValidateDataListener<UpdateAndDeleteToGetHelpModel> {
            override fun onSuccess(data: UpdateAndDeleteToGetHelpModel) {
                bindAddToGetHelp.addToGetHelpBtnSave.isEnabled = true
                bindAddToGetHelp.addToGetHelpSavePB.visibility = View.INVISIBLE
                if (data.success) {
                    Toast.makeText(
                        MainActivity.mContext,
                        "اگهی در صف قرار گرفت!",
                        Toast.LENGTH_SHORT
                    ).show()
                    MainActivity.mContext.supportFragmentManager.popBackStack()
                }
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate)
                    Toast.makeText(MainActivity.mContext , "noValid", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Throwable) {
                bindAddToGetHelp.addToGetHelpBtnSave.isEnabled = true
                bindAddToGetHelp.addToGetHelpSavePB.visibility = View.INVISIBLE
            }
        }).toGetHelpData(
            info
        )
    }

    fun updateSetInfo(
        title:String,
        shortDescription:String,
        description:String,
        category:String,
        bindAddToGetHelp: AddToGetHelpFragmentBinding,
        imageList:List<String>
    ){
        bindAddToGetHelp.addToGetHelpTitle.setText(title)

        val categoryTitleList = mutableListOf<String>()
        for (item in MainActivity.mContext.categoriesList){
            categoryTitleList += item.title
        }

        val adapter = ArrayAdapter(MainActivity.mContext, R.layout.spinner_color_layout, categoryTitleList)
        adapter.setDropDownViewResource(R.layout.spinner_popup_layout)
        bindAddToGetHelp.addToGetHelpCategory.adapter = adapter
        bindAddToGetHelp.addToGetHelpCategory.setSelection(category?.toInt()?.minus(1)?:0)

        bindAddToGetHelp.addToGetHelpShortDescreption.setText(shortDescription)
        bindAddToGetHelp.addToGetHelpDescreption.setText(description)

        if (imageList.count() > 0){
            val list = listOf(
                bindAddToGetHelp.addToGetHelpImage1,
                bindAddToGetHelp.addToGetHelpImage2,
                bindAddToGetHelp.addToGetHelpImage3,
                bindAddToGetHelp.addToGetHelpImage4,
                bindAddToGetHelp.addToGetHelpImage5
            )
            val picasso : PicassoCreator by inject()
            for (item in 0 until imageList.count()){
                picasso.getImage(MainActivity.mContext.baseDownloadUrl + imageList[item], list[item], null)
            }
        }
    }
}