package com.tinyDeveloper.yavaran.publicClass

import android.annotation.SuppressLint
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.adapter.ToHelpAdapter
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.GetAllToHelpModel
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.ToHelp
import com.tinyDeveloper.yavaran.api.presenters.categories.FindToHelpByCategoryPresenter
import com.tinyDeveloper.yavaran.databinding.ShowToHelpFragmentBinding

class ShowToHelpByCategory {
    fun getToHelpsByCategoryId(id:String, category:String, bindShowToHelp: ShowToHelpFragmentBinding){
        FindToHelpByCategoryPresenter(object : ValidateDataListener<GetAllToHelpModel> {
            @SuppressLint("WrongConstant")
            override fun onSuccess(data: GetAllToHelpModel) {
                bindShowToHelp.showToHelpOtherPB.visibility = View.INVISIBLE
                if (data.status){
                    val list = mutableListOf<ToHelp>()
                    for (item in data.toHelps){
                        if (list.count() < 5){
                            if (item.toHelp.id != id){
                                list += item
                            }
                        }
                    }
                    val adapter = ToHelpAdapter(list)
                    bindShowToHelp.showToHelpOther.layoutManager =
                        GridLayoutManager(MainActivity.mContext,1 , LinearLayout.VERTICAL, false)
                    bindShowToHelp.showToHelpOther.adapter = adapter
                }
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate)
                    Toast.makeText(MainActivity.mContext, "سرور پاسخگو نبود", Toast.LENGTH_LONG).show()
            }

            override fun onError(error: Throwable) {
                bindShowToHelp.showToHelpOtherPB.visibility = View.INVISIBLE
            }
        }).categoriesData("findToHelp", category)
    }
}