package com.tinyDeveloper.yavaran.publicClass

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Button
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R

class ShowInternetErrorAlert {
    lateinit var alertDialog:AlertDialog
    lateinit var alertDialogBuilder:AlertDialog.Builder
    @SuppressLint("InflateParams")
    fun createAlert(again: () -> Unit){
        val view = LayoutInflater.from(MainActivity.mContext).inflate(R.layout.internet_error_layout, null)
        val btnAgain: Button = view.findViewById(R.id.internetErrorBtnAgain)
        alertDialogBuilder = AlertDialog.Builder(MainActivity.mContext, R.style.dialogMain)
            .setView(view)
            .setCancelable(false)
        alertDialog = alertDialogBuilder.create()
        btnAgain.setOnClickListener {
            alertDialog.dismiss()
            again()
        }
        alertDialog.show()
    }
}