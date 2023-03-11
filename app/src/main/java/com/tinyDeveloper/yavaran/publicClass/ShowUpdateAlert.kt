package com.tinyDeveloper.yavaran.publicClass

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R

class ShowUpdateAlert {
    lateinit var alertDialog:AlertDialog
    lateinit var alertDialogBuilder:AlertDialog.Builder
    @SuppressLint("InflateParams")
    fun createAlert(title:String, text:String, update:() -> Unit){
        val view = LayoutInflater.from(MainActivity.mContext).inflate(R.layout.update_alert_layout, null)
        val titleView:TextView = view.findViewById(R.id.updateAlertTitle)
        val textView:TextView = view.findViewById(R.id.updateAlertText)
        val btnUpdate:Button = view.findViewById(R.id.updateAlertBtnUpdate)
        val btnClose:Button = view.findViewById(R.id.updateAlertBtnClose)
        alertDialogBuilder = AlertDialog.Builder(MainActivity.mContext, R.style.dialogMain)
            .setView(view)
            .setCancelable(false)
        titleView.text = title
        textView.text = text
        btnUpdate.setOnClickListener {
            update()
        }
        btnClose.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}