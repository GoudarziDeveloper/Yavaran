package com.tinyDeveloper.yavaran.publicClass

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import de.hdodenhof.circleimageview.CircleImageView

class PicassoCreator {
    fun getImage(url:String, imageView: ImageView?, circleImageView: CircleImageView?){
        if(imageView != null){
            Picasso.with(MainActivity.mContext)
                .load(url)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(imageView)
        }
        if (circleImageView != null){
            Picasso.with(MainActivity.mContext)
                .load(url)
                .placeholder(R.drawable.user_image)
                .error(R.drawable.user_image)
                .into(circleImageView)
        }
    }
}