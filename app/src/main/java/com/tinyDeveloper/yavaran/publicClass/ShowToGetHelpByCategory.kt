package com.tinyDeveloper.yavaran.publicClass

import android.annotation.SuppressLint
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.adapter.ToGetHelpAdapter
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.GetAllToGetHelpModel
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.ToGetHelp
import com.tinyDeveloper.yavaran.api.presenters.categories.FindToGetHelpByCategoryPresenter
import com.tinyDeveloper.yavaran.databinding.ShowToGetHelpFragmentBinding

class ShowToGetHelpByCategory {
    fun findToGetHelpByCategory(id:String, category: String, bindShowToGetHelp: ShowToGetHelpFragmentBinding){
        FindToGetHelpByCategoryPresenter(object : ValidateDataListener<GetAllToGetHelpModel> {
            @SuppressLint("WrongConstant")
            override fun onSuccess(data: GetAllToGetHelpModel) {
                val list = mutableListOf<ToGetHelp>()
                for (item in data.toGetHelps){
                    if (list.count() < 5){
                        if (item.toGetHelp.id != id){
                            list += item
                        }
                    }
                }
                val adapter = ToGetHelpAdapter(list)
                bindShowToGetHelp.showToGetHelpOther.layoutManager =
                    GridLayoutManager(MainActivity.mContext,1 , LinearLayout.VERTICAL, false)
                bindShowToGetHelp.showToGetHelpOther.adapter = adapter
                bindShowToGetHelp.showToGetHelpOtherPB.visibility = View.INVISIBLE
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate)
                    Toast.makeText(MainActivity.mContext, "noValid", Toast.LENGTH_LONG).show()
                bindShowToGetHelp.showToGetHelpOtherPB.visibility = View.INVISIBLE
            }

            override fun onError(error: Throwable) {
                Toast.makeText(MainActivity.mContext, error.toString(), Toast.LENGTH_LONG).show()
                bindShowToGetHelp.showToGetHelpOtherPB.visibility = View.INVISIBLE
            }
        }).categoriesData("findToGetHelp", category)
    }
}