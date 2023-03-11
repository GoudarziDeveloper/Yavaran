package com.tinyDeveloper.yavaran.publicClass

import android.graphics.Bitmap
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.AddToHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.AddToHelpModel
import com.tinyDeveloper.yavaran.api.presenters.toHelp.AddToHelpPresenter
import com.tinyDeveloper.yavaran.databinding.AddToHelpFragmentBinding
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.io.ByteArrayOutputStream

class AddToHelp: KoinComponent {
    fun addToHelp(info: AddToHelpBodyModel, bindAddToHelp:AddToHelpFragmentBinding){
        AddToHelpPresenter(object : ValidateDataListener<AddToHelpModel> {
            override fun onSuccess(data: AddToHelpModel) {
                bindAddToHelp.addToHelpSavePB.visibility = View.INVISIBLE
                if (data.status){
                    MainActivity.mContext.supportFragmentManager.popBackStack()
                    Toast.makeText(MainActivity.mContext, "در صف برسی قرار گرفت!", Toast.LENGTH_LONG).show()
                }
                else
                    Toast.makeText(MainActivity.mContext, "سرور پاسخگو نبود!", Toast.LENGTH_LONG).show()
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate)
                    Toast.makeText(MainActivity.mContext, "سرور پاسخگو نبود!", Toast.LENGTH_LONG).show()
            }

            override fun onError(error: Throwable) {
                bindAddToHelp.addToHelpSavePB.visibility = View.INVISIBLE
            }
        }).toHelpData(info)
    }
}