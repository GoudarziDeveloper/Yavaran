package com.tinyDeveloper.yavaran.api.presenters.toHelp

import android.view.View
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ApiServices
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.GetAllToHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.GetAllToHelpModel
import com.tinyDeveloper.yavaran.publicClass.Network
import com.tinyDeveloper.yavaran.publicClass.ShowInternetErrorAlert
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetAllToHelpPresenter(val mListener:ValidateDataListener<GetAllToHelpModel>): KoinComponent {
    fun toHelpData(info:GetAllToHelpBodyModel, isSwiping:Boolean){
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
                .getAllToHelp(MainActivity.mContext.toHelpUrl, info)
                .enqueue(object : Callback<GetAllToHelpModel> {
                    override fun onResponse(
                        call: Call<GetAllToHelpModel>,
                        response: Response<GetAllToHelpModel>
                    ) {
                        if (!isSwiping){
                            MainActivity.mContext.binding.mainStayView.visibility = View.INVISIBLE
                            MainActivity.mContext.binding.mainStayText.visibility = View.INVISIBLE
                            MainActivity.mContext.binding.mainStayPB.visibility = View.INVISIBLE
                        }
                        val data = response.body()
                        if (response.isSuccessful)
                            mListener.onInvalidate(true)
                        else
                            mListener.onInvalidate(false)
                        if (data != null)
                            mListener.onSuccess(data)
                    }

                    override fun onFailure(call: Call<GetAllToHelpModel>, t: Throwable) {
                        if (!isSwiping){
                            MainActivity.mContext.binding.mainStayView.visibility = View.INVISIBLE
                            MainActivity.mContext.binding.mainStayText.visibility = View.INVISIBLE
                            MainActivity.mContext.binding.mainStayPB.visibility = View.INVISIBLE
                        }
                        val showInternetErrorAlert: ShowInternetErrorAlert by inject()
                        showInternetErrorAlert.createAlert {
                            toHelpData(info, isSwiping)
                        }
                        mListener.onError(t)
                    }
                })
        }else{
            val showInternetErrorAlert: ShowInternetErrorAlert by inject()
            showInternetErrorAlert.createAlert {
                toHelpData(info, isSwiping)
            }
        }
    }
}