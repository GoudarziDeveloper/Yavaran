package com.tinyDeveloper.yavaran.publicClass

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.IndicatorView.draw.data.RtlMode
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.adapter.MessageAlertImageSliderAdapter

class ShowMessageAlert {
    lateinit var alertDialog: AlertDialog
    lateinit var alertDialogBuilder: AlertDialog.Builder
    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("InflateParams")
    fun createAlert(imageList:List<String>,title:String, text:String){
        val view = LayoutInflater.from(MainActivity.mContext).inflate(R.layout.message_alert_layout, null)
        val imageSlider: SliderView = view.findViewById(R.id.messageAlertImage)
        val titleView: TextView = view.findViewById(R.id.messageAlertTitle)
        val textView: TextView = view.findViewById(R.id.messageAlertText)
        val btnOk: Button = view.findViewById(R.id.messageAlertBtnOk)
        alertDialogBuilder = AlertDialog.Builder(MainActivity.mContext, R.style.dialogMain)
            .setView(view)
            .setCancelable(false)
        alertDialog = alertDialogBuilder.create()
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM)
        //imageSlider.setSliderTransformAnimation(SliderAnimations.VERTICALSHUTTRANSFORMATION)
        imageSlider.setSliderTransformAnimation(SliderAnimations.SPINNERTRANSFORMATION)
        //imageSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        //imageSlider.indicatorSelectedColor = Color.BLUE
        //imageSlider.indicatorUnselectedColor = Color.WHITE
        //imageSlider.sliderPager.layoutDirection = View.LAYOUT_DIRECTION_RTL
        //imageSlider.layoutDirection = View.LAYOUT_DIRECTION_RTL
        imageSlider.setSliderAdapter(MessageAlertImageSliderAdapter(imageList))
        imageSlider.scrollTimeInSec = 2
        imageSlider.autoCycleDirection = View.LAYOUT_DIRECTION_RTL
        imageSlider.currentPagePosition = imageList.count() - 1
        imageSlider.startAutoCycle()
        titleView.text = title
        textView.text = text
        btnOk.text = "باشه"
        btnOk.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}