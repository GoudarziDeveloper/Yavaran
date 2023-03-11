package com.tinyDeveloper.yavaran.publicClass

import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R

class AddToGetHelpSetImages {
    fun setToGetHelpImage(image: Uri){
        val imageViews = listOf<ImageView>(
            MainActivity.mContext.findViewById(R.id.addToGetHelpImage1),
            MainActivity.mContext.findViewById(R.id.addToGetHelpImage2),
            MainActivity.mContext.findViewById(R.id.addToGetHelpImage3),
            MainActivity.mContext.findViewById(R.id.addToGetHelpImage4),
            MainActivity.mContext.findViewById(R.id.addToGetHelpImage5)
        )
        for (item in 0..imageViews.count()){
            if (item < 5){
                val def = ContextCompat.getDrawable(MainActivity.mContext, R.drawable.place_holder)?.toBitmap()
                val newImage = imageViews[item].drawable.toBitmap()
                if (newImage.sameAs(def)) {
                    imageViews[item].setImageURI(image)
                    return
                }
            }else{
                Toast.makeText(MainActivity.mContext, "حداکثر 5 تصویر محاز است!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}