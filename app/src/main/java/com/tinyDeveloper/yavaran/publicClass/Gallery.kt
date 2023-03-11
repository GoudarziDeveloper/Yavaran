package com.tinyDeveloper.yavaran.publicClass

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult
import com.tinyDeveloper.yavaran.MainActivity

class Gallery {
    fun pickImage(requestCode:Int){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if (MainActivity.mContext.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager. PERMISSION_GRANTED){
                selectImage(requestCode)
            }else{
                MainActivity.mContext.requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCode)
            }
        }else{
            selectImage(requestCode)
        }
    }
    private fun selectImage(requestCode: Int){
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"
        pickIntent.putExtra("crop",true)
        pickIntent.putExtra("scale",true)
        pickIntent.putExtra("aspectX",9)
        pickIntent.putExtra("aspectY",9)
        startActivityForResult(MainActivity.mContext, pickIntent, requestCode, null)
    }
}