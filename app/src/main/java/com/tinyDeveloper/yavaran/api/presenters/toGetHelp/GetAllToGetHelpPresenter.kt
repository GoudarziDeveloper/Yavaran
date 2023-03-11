package com.tinyDeveloper.yavaran.api.presenters.toGetHelp

import android.view.View
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ApiServices
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.GetAllToGetHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.GetAllToGetHelpModel
import com.tinyDeveloper.yavaran.publicClass.Network
import com.tinyDeveloper.yavaran.publicClass.ShowInternetErrorAlert
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Response

class GetAllToGetHelpPresenter(val mListener: ValidateDataListener<GetAllToGetHelpModel>): KoinComponent {
    fun toGetHelpData(info:GetAllToGetHelpBodyModel,
                      isSwiping:Boolean = false){
        val network: Network by inject()
        if (network.isNetworkAvailable(MainActivity.mContext)){
            if (!isSwiping){
                MainActivity.mContext.binding.mainStayView.visibility = View.VISIBLE
                MainActivity.mContext.binding.mainStayText.visibility = View.VISIBLE
                MainActivity.mContext.binding.mainStayPB.visibility = View.VISIBLE
            }
            val apiServices : ApiServices by inject()
            apiServices
                .getApi()
                .getAllToGetHelp(MainActivity.mContext.toGetHelpUrl, info)
                .enqueue(object : retrofit2.Callback<GetAllToGetHelpModel>{
                    override fun onResponse(call: Call<GetAllToGetHelpModel>, response: Response<GetAllToGetHelpModel>) {
                        if (!isSwiping){
                            MainActivity.mContext.binding.mainStayView.visibility = View.INVISIBLE
                            MainActivity.mContext.binding.mainStayText.visibility = View.INVISIBLE
                            MainActivity.mContext.binding.mainStayPB.visibility = View.INVISIBLE
                        }
                        val data = response.body()
                        if (response.isSuccessful){
                            mListener.onInvalidate(true)
                        }else{
                            mListener.onInvalidate(false)
                        }
                        if (data != null){
                            mListener.onSuccess(data)
                        }
                    }
                    override fun onFailure(call: Call<GetAllToGetHelpModel>, t: Throwable) {
                        if(isSwiping){
                            MainActivity.mContext.binding.mainStayView.visibility = View.INVISIBLE
                            MainActivity.mContext.binding.mainStayText.visibility = View.INVISIBLE
                            MainActivity.mContext.binding.mainStayPB.visibility = View.INVISIBLE
                        }
                        val showInternetErrorAlert: ShowInternetErrorAlert by inject()
                        showInternetErrorAlert.createAlert {
                            toGetHelpData(info)
                        }
                        mListener.onError(t)
                    }
                })
        }else{
            val showInternetErrorAlert: ShowInternetErrorAlert by inject()
            showInternetErrorAlert.createAlert {
                toGetHelpData(info)
            }
        }
    }
}