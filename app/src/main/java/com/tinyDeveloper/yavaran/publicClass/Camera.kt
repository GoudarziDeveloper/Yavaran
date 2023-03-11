package com.tinyDeveloper.yavaran.publicClass

import android.content.ContentValues
import android.content.Intent
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult
import com.tinyDeveloper.yavaran.MainActivity

class Camera {
    fun openCamera(requestCode:Int){
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "انتخاب تصویر مشتری")
        values.put(MediaStore.Images.Media.DESCRIPTION, "این تصویر برای مشتری یادمان شاپ در نظر گرفته میشود")
        MainActivity.mContext.imageUri =
            MainActivity.mContext.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra("aspectX",9)
        cameraIntent.putExtra("aspectY",9)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, MainActivity.mContext.imageUri)
        startActivityForResult(MainActivity.mContext, cameraIntent, requestCode, null)
    }
}