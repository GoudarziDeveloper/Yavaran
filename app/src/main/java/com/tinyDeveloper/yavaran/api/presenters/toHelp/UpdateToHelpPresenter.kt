package com.tinyDeveloper.yavaran.api.presenters.toHelp

import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ApiServices
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.AddToHelpBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.UpdateAndDeleteToHelpModel
import com.tinyDeveloper.yavaran.publicClass.Network
import com.tinyDeveloper.yavaran.publicClass.ShowInternetErrorAlert
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateToHelpPresenter(val mListener:ValidateDataListener<UpdateAndDeleteToHelpModel>):KoinComponent {
    fun toHelpData(info: AddToHelpBodyModel){
        val network: Network by inject()
        if (network.isNetworkAvailable(MainActivity.mContext)){
            val apiServices : ApiServices by inject()
            apiServices
                .getApi()
                .updateToHelp(MainActivity.mContext.toHelpUrl, info)
                .enqueue(object : Callback<UpdateAndDeleteToHelpModel> {
                    override fun onResponse(
                        call: Call<UpdateAndDeleteToHelpModel>,
                        response: Response<UpdateAndDeleteToHelpModel>
                    ) {
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

                    override fun onFailure(call: Call<UpdateAndDeleteToHelpModel>, t: Throwable) {
                        val showInternetErrorAlert: ShowInternetErrorAlert by inject()
                        showInternetErrorAlert.createAlert {
                            toHelpData(info)
                        }
                        mListener.onError(t)
                    }
                })
        }else{
            val showInternetErrorAlert: ShowInternetErrorAlert by inject()
            showInternetErrorAlert.createAlert {
                toHelpData(info)
            }
        }
    }
}