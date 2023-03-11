package com.tinyDeveloper.yavaran.publicClass

import android.app.AlertDialog
import android.widget.Button
import android.widget.Toast
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.AddToGetHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.UpdateAndDeleteToGetHelpModel
import com.tinyDeveloper.yavaran.api.presenters.toGetHelp.UpdateToGetHelpPresenter

class CompletedToGetHelp {
    fun completed(info: AddToGetHelpBodyModel, btnCompleted: Button, completedAlertDialog: AlertDialog){
        UpdateToGetHelpPresenter(object : ValidateDataListener<UpdateAndDeleteToGetHelpModel> {
            override fun onSuccess(data: UpdateAndDeleteToGetHelpModel) {
                if (data.success){
                    completedAlertDialog.dismiss()
                    Toast.makeText(MainActivity.mContext, "با موفقیت برداشته شد!", Toast.LENGTH_SHORT).show()
                    btnCompleted.isEnabled = false
                }
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate){
                    Toast.makeText(MainActivity.mContext, "سرور پاسخگو نبود!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(error: Throwable) {
                completedAlertDialog.dismiss()
            }
        }).toGetHelpData(info)
    }
}