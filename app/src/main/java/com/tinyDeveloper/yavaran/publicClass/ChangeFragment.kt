package com.tinyDeveloper.yavaran.publicClass

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.dataModels.KeyValues

class ChangeFragment {
    fun newArgumentsFragment(
        fragment: Fragment ,
        isReplace:Boolean = false,
        isBack: Boolean = false,
        title:String? = null,
        name:String? = null,
        listArguments:List<KeyValues>? = null,
        listArguments2:List<KeyValues>? = null
    ){
        val activity = MainActivity.mContext as AppCompatActivity
        if (!isReplace){
            if (listArguments != null){
                val bundle = Bundle()
                for (item in listArguments){
                    bundle.putString(item.key , item.Value)
                }
                if (listArguments2 != null){
                    for (item in listArguments2){
                        bundle.putString(item.key , item.Value)
                    }
                }
                fragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .addToBackStack(name)
                    .replace(R.id.switcherFragment, fragment)
                    .commit()
                MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = title
                if (isBack)
                    MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.VISIBLE
            }else{
                activity.supportFragmentManager.beginTransaction()
                    .addToBackStack(name)
                    .replace(R.id.switcherFragment, fragment)
                    .commit()
                MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = title
                if (isBack)
                    MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.VISIBLE
            }
        }else{
            if (listArguments != null){
                val bundle = Bundle()
                for (item in listArguments){
                    bundle.putString(item.key , item.Value)
                }
                if (listArguments2 != null){
                    Toast.makeText(MainActivity.mContext, listArguments2.count().toString(), Toast.LENGTH_LONG).show()
                    for (item in listArguments2){
                        bundle.putString(item.key , item.Value)
                    }
                }
                fragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.switcherFragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
                MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = title
                if (isBack)
                    MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.VISIBLE
            }else{
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.switcherFragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
                MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = title
                if (isBack)
                    MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.VISIBLE
            }
        }
    }
}