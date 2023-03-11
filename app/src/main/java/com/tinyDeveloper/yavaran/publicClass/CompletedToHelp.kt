package com.tinyDeveloper.yavaran.publicClass

import android.app.AlertDialog
import android.widget.Button
import android.widget.Toast
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.AddToHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.UpdateAndDeleteToHelpModel
import com.tinyDeveloper.yavaran.api.presenters.toHelp.UpdateToHelpPresenter

class CompletedToHelp {
    fun completed(info: AddToHelpBodyModel, btnCompleted: Button, completedAlertDialog:AlertDialog){
        UpdateToHelpPresenter(object : ValidateDataListener<UpdateAndDeleteToHelpModel> {
            override fun onSuccess(data: UpdateAndDeleteToHelpModel) {
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
        }).toHelpData(info)
    }
}