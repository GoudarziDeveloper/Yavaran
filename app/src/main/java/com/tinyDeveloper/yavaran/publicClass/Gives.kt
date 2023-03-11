package com.tinyDeveloper.yavaran.publicClass

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.adapter.ToHelpAdapter
import com.tinyDeveloper.yavaran.adapter.ToHelpEditAdapter
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.GetAllToHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.GetAllToHelpModel
import com.tinyDeveloper.yavaran.api.presenters.toHelp.GetAllToHelpPresenter
import com.tinyDeveloper.yavaran.databinding.GivesFragmentBinding

class Gives {
    fun getAllToHelps(info: GetAllToHelpBodyModel, bindGives:GivesFragmentBinding, isSwiping:Boolean = false){
        GetAllToHelpPresenter(object : ValidateDataListener<GetAllToHelpModel> {
            @RequiresApi(Build.VERSION_CODES.M)
            @SuppressLint("WrongConstant")
            override fun onSuccess(data: GetAllToHelpModel) {
                if (data.status){
                    if (info.QueryType == "getAll"){
                        val adapter = ToHelpAdapter(data.toHelps)
                        bindGives.givesList.layoutManager =
                            LinearLayoutManager(MainActivity.mContext)
                        bindGives.givesList.adapter = adapter
                        var loading = true
                        var visibleItemCount: Int
                        var totalItemCount: Int
                        var pastVisibleItems: Int
                            //bindGives.givesList.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                        bindGives.givesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                if (dy > 0) {
                                    visibleItemCount =
                                        (bindGives.givesList.layoutManager as LinearLayoutManager).childCount
                                    totalItemCount =
                                        (bindGives.givesList.layoutManager as LinearLayoutManager).itemCount
                                    pastVisibleItems =
                                        (bindGives.givesList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                                    if (loading) {
                                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                                            loading = false
                                            bindGives.givesPB.visibility = View.VISIBLE
                                            GetAllToHelpPresenter(object :
                                                ValidateDataListener<GetAllToHelpModel> {
                                                @SuppressLint("NotifyDataSetChanged")
                                                override fun onSuccess(data: GetAllToHelpModel) {
                                                    bindGives.givesPB.visibility = View.GONE
                                                    if (data.status) {
                                                        adapter.list += data.toHelps
                                                        adapter.notifyItemInserted(data.toHelps.count() - 1)
                                                        adapter.notifyDataSetChanged()
                                                        loading = true
                                                    }
                                                }

                                                override fun onInvalidate(isValidate: Boolean) {
                                                    bindGives.givesPB.visibility = View.GONE
                                                }

                                                override fun onError(error: Throwable) {
                                                    bindGives.givesPB.visibility = View.GONE
                                                }
                                            }).toHelpData(
                                                GetAllToHelpBodyModel(
                                                    "getAll",
                                                    null,
                                                    10,
                                                    totalItemCount
                                                ), true
                                            )
                                        }
                                    }
                                }
                            }
                        })
                    }else if (info.QueryType == "findUserToHelps"){
                        val adapter = ToHelpEditAdapter(data.toHelps)
                        bindGives.givesList.layoutManager =
                            LinearLayoutManager(MainActivity.mContext)
                        bindGives.givesList.adapter = adapter
                    }

                }
                else
                    Toast.makeText(MainActivity.mContext, "سرور پاسخگو نبود", Toast.LENGTH_LONG).show()
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate)
                    Toast.makeText(MainActivity.mContext, "سرور پاسخگو نبود", Toast.LENGTH_LONG).show()
            }

            override fun onError(error: Throwable) {
            }
        }).toHelpData(info, isSwiping)
    }
}