package com.tinyDeveloper.yavaran.publicClass

import android.graphics.Bitmap
import android.util.Base64
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.io.ByteArrayOutputStream

class ImageConverter: KoinComponent{
    fun convertImages(imageViews:List<ImageView>): MutableList<String>{
        val images = mutableListOf<String>()
        for (item in 0 until imageViews.count()){
            val def = ContextCompat.getDrawable(MainActivity.mContext, R.drawable.place_holder)?.toBitmap()
            val newImage = imageViews[item].drawable.toBitmap()
            val converter : Converter by inject()
            if (!newImage.sameAs(def)) {
                images += "data:image/png;base64," +
                        encodeToBase64(
                            converter.drawableToBitmap(imageViews[item].drawable))
            }
        }
        return images
    }
    fun encodeToBase64(image: Bitmap?): String? {
        if (image != null){
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b: ByteArray = baos.toByteArray()
            return Base64.encodeToString(b, Base64.DEFAULT)
        }
        return null
    }
}