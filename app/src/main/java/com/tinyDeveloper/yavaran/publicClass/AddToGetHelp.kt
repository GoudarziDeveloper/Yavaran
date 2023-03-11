package com.tinyDeveloper.yavaran.publicClass

import android.view.View
import android.widget.Toast
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.AddToGetHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.AddToGetHelpModel
import com.tinyDeveloper.yavaran.api.presenters.toGetHelp.AddToGetHelpPresenter
import com.tinyDeveloper.yavaran.databinding.AddToGetHelpFragmentBinding
import org.koin.standalone.KoinComponent

class AddToGetHelp: KoinComponent {
    fun addNew(
        info: AddToGetHelpBodyModel,
        bindAddToGetHelp: AddToGetHelpFragmentBinding
    ){
        AddToGetHelpPresenter(object : ValidateDataListener<AddToGetHelpModel> {
            override fun onSuccess(data: AddToGetHelpModel) {
                bindAddToGetHelp.addToGetHelpBtnSave.isEnabled = true
                bindAddToGetHelp.addToGetHelpSavePB.visibility = View.INVISIBLE
                if (data.status) {
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
}