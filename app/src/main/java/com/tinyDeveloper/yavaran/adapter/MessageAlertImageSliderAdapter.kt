package com.tinyDeveloper.yavaran.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.smarteist.autoimageslider.SliderViewAdapter
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.publicClass.PicassoCreator
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class MessageAlertImageSliderAdapter(
    val imageList:List<String>): SliderViewAdapter<MessageAlertImageSliderAdapter.ViewHolder>(),
    KoinComponent {
    inner class ViewHolder(view:View): SliderViewAdapter.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.messageAlertImageItem)
        fun binder(item:String){
            val picassoCreator: PicassoCreator by inject()
            picassoCreator.getImage(MainActivity.mContext.baseDownloadUrl + item, image, null)
        }
    }

    override fun getCount(): Int = imageList.count()

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        return ViewHolder(LayoutInflater.from(MainActivity.mContext).inflate(R.layout.message_slider_item_layout, parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binder(imageList[position])
    }
}