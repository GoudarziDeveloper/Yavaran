package com.tinyDeveloper.yavaran.publicClass

import android.app.AlertDialog
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R

class ShowDisableAppAlert {
    lateinit var alertDialog: AlertDialog
    lateinit var alertDialogBuilder: AlertDialog.Builder
    fun createAlert(
        title:String,
        message:String,
        btnText:String,
        src:Drawable?,
        close:() -> Unit
    ){
        val view = LayoutInflater.from(MainActivity.mContext).inflate(R.layout.internet_error_layout, null)
        val image: ImageView = view.findViewById(R.id.internetErrorImage)
        val btn: Button = view.findViewById(R.id.internetErrorBtnAgain)
        val titleView: TextView = view.findViewById(R.id.internetErrorTitle)
        val messageView: TextView = view.findViewById(R.id.internetErrorMessage)
        if (src != null)
            image.setImageDrawable(src)
        titleView.text = title
        messageView.text = message
        btn.text = btnText
        alertDialogBuilder = AlertDialog.Builder(MainActivity.mContext, R.style.dialogMain)
            .setView(view)
            .setCancelable(false)
        btn.setOnClickListener {
            close()
        }
        alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}