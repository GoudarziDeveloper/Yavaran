package com.tinyDeveloper.yavaran.publicClass

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.adapter.ToGetHelpAdapter
import com.tinyDeveloper.yavaran.adapter.ToGetHelpEditAdapter
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.GetAllToGetHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.GetAllToGetHelpModel
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.ToGetHelp
import com.tinyDeveloper.yavaran.api.presenters.toGetHelp.GetAllToGetHelpPresenter
import com.tinyDeveloper.yavaran.databinding.HelpFragmentBinding

class Help {
    fun getAllToGetHelps(info: GetAllToGetHelpBodyModel, bindHelp:HelpFragmentBinding, type:String){
        GetAllToGetHelpPresenter(object : ValidateDataListener<GetAllToGetHelpModel> {
            override fun onSuccess(data: GetAllToGetHelpModel) {
                if (data.status){
                    if (type == "findUserToGetHelps"){
                        val adapter = ToGetHelpEditAdapter(data.toGetHelps,
                            MainActivity.mContext.binding.includeToolbar.toolbarTitle.text.toString())
                        bindHelp.helpToGetHelpList.layoutManager =
                            LinearLayoutManager(MainActivity.mContext)
                        bindHelp.helpToGetHelpList.adapter = adapter
                    }else if (type == "findNeededToGetHelps"){
                        val adapter = ToGetHelpEditAdapter(data.toGetHelps,
                            MainActivity.mContext.binding.includeToolbar.toolbarTitle.text.toString())
                        bindHelp.helpToGetHelpList.layoutManager =
                            LinearLayoutManager(MainActivity.mContext)
                        bindHelp.helpToGetHelpList.adapter = adapter
                    } else{

                        val adapter = ToGetHelpAdapter(data.toGetHelps)
                        bindHelp.helpToGetHelpList.layoutManager =
                            LinearLayoutManager(MainActivity.mContext)
                        bindHelp.helpToGetHelpList.adapter = adapter

                        var loading = true
                        var pastVisibleItems: Int
                        var visibleItemCount: Int
                        var totalItemCount: Int
                        bindHelp.helpToGetHelpList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                if (dy > 0) {
                                    visibleItemCount = (bindHelp.helpToGetHelpList.layoutManager as LinearLayoutManager).childCount
                                    totalItemCount = (bindHelp.helpToGetHelpList.layoutManager as LinearLayoutManager).itemCount
                                    pastVisibleItems =
                                        (bindHelp.helpToGetHelpList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                                    if (loading) {
                                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                                            loading = false
                                            bindHelp.helpPB.visibility = View.VISIBLE
                                            GetAllToGetHelpPresenter(object : ValidateDataListener<GetAllToGetHelpModel> {
                                                @SuppressLint("NotifyDataSetChanged")
                                                override fun onSuccess(data: GetAllToGetHelpModel) {
                                                    bindHelp.helpPB.visibility = View.GONE
                                                    if (data.status){
                                                        adapter.list = adapter.list + data.toGetHelps
                                                        adapter.notifyItemInserted(data.toGetHelps.count() - 1)
                                                        adapter.notifyDataSetChanged()
                                                        loading = true
                                                    }
                                                }

                                                override fun onInvalidate(isValidate: Boolean) {
                                                    if (!isValidate)
                                                        bindHelp.helpPB.visibility = View.GONE
                                                }

                                                override fun onError(error: Throwable) {
                                                    bindHelp.helpPB.visibility = View.GONE
                                                }
                                            }).toGetHelpData(GetAllToGetHelpBodyModel("getAll", null, 10, totalItemCount), true)
                                        }
                                    }
                                }
                            }
                        })

                    }
                }else{
                    Toast.makeText(MainActivity.mContext, "موردی یافت نشد", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate)
                    Toast.makeText(MainActivity.mContext, "سرور پاسخگو نیست", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Throwable) {
            }
        }).toGetHelpData(info, true)
    }
}