package com.tinyDeveloper.yavaran.publicClass

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

class Converter {

    fun StringToBitMap(image: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(image, Base64.DEFAULT)
            val inputStream: InputStream = ByteArrayInputStream(encodeByte)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.message
            null
        }
    }
    fun BitMapToString(bitmap: Bitmap, isPng:Boolean): String? {
        val baos = ByteArrayOutputStream()
        if (isPng) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 20, baos)
            //Toast.makeText(this , "PNG" , Toast.LENGTH_LONG).show()
        }
        else{
            //Toast.makeText(this , "JPEG" , Toast.LENGTH_LONG).show()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        }
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun BitmapToByteArray(bmp:Bitmap?, isPng:Boolean) : ByteArray{
        val stream = ByteArrayOutputStream()
        //TODO تغییر کیفیت می تواند مفید باشد
        if (isPng) {
            bmp?.compress(Bitmap.CompressFormat.PNG, 30, stream)
            //Toast.makeText(this , "PNG" , Toast.LENGTH_LONG).show()
        }
        else{
            //Toast.makeText(this , "JPEG" , Toast.LENGTH_LONG).show()
            bmp?.compress(Bitmap.CompressFormat.JPEG, 30, stream)
        }
        val byteArray = stream.toByteArray()
        return byteArray
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable
            if (bitmapDrawable.bitmap != null) {
                return bitmapDrawable.bitmap
            }
        }
        var bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
        } else {
            Bitmap.createBitmap(drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }
}