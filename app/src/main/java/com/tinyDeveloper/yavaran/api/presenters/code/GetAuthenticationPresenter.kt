package com.tinyDeveloper.yavaran.api.presenters.code

import android.view.View
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ApiServices
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.code.MobileApiModel
import com.tinyDeveloper.yavaran.api.apiModels.code.CodeApiModel
import com.tinyDeveloper.yavaran.publicClass.Network
import com.tinyDeveloper.yavaran.publicClass.ShowInternetErrorAlert
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Response

class GetAuthenticationPresenter(val mListener: ValidateDataListener<CodeApiModel>): KoinComponent {
    fun authenticationData(mobile:String){
        val network: Network by inject()
        if (network.isNetworkAvailable(MainActivity.mContext)){
            MainActivity.mContext.binding.mainStayView.visibility = View.VISIBLE
            MainActivity.mContext.binding.mainStayText.visibility = View.VISIBLE
            MainActivity.mContext.binding.mainStayPB.visibility = View.VISIBLE
            val apiServices : ApiServices by inject()
            apiServices
                .getApi()
                .getAuthenticationCode(MainActivity.mContext.sendCodeUrl, MobileApiModel(mobile))
                .enqueue(object : retrofit2.Callback<CodeApiModel>{
                    override fun onResponse(call: Call<CodeApiModel>, response: Response<CodeApiModel>) {
                        MainActivity.mContext.binding.mainStayView.visibility = View.INVISIBLE
                        MainActivity.mContext.binding.mainStayText.visibility = View.INVISIBLE
                        MainActivity.mContext.binding.mainStayPB.visibility = View.INVISIBLE
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
                    override fun onFailure(call: Call<CodeApiModel>, t: Throwable) {
                        MainActivity.mContext.binding.mainStayView.visibility = View.INVISIBLE
                        MainActivity.mContext.binding.mainStayText.visibility = View.INVISIBLE
                        MainActivity.mContext.binding.mainStayPB.visibility = View.INVISIBLE
                        val showInternetErrorAlert: ShowInternetErrorAlert by inject()
                        showInternetErrorAlert.createAlert {
                            authenticationData(mobile)
                        }
                        mListener.onError(t)
                    }
                })
        } else{
            val showInternetErrorAlert: ShowInternetErrorAlert by inject()
            showInternetErrorAlert.createAlert {
                authenticationData(mobile)
            }
        }
    }
}