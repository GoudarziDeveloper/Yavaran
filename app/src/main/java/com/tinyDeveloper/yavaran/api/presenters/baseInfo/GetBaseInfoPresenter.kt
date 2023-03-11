package com.tinyDeveloper.yavaran.api.presenters.baseInfo

import android.widget.Toast
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ApiServices
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.baseInfo.GetBaseInfoBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.baseInfo.GetBaseInfoModel
import com.tinyDeveloper.yavaran.publicClass.Network
import com.tinyDeveloper.yavaran.publicClass.ShowInternetErrorAlert
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetBaseInfoPresenter(val mListener:ValidateDataListener<GetBaseInfoModel>): KoinComponent {
    fun baseInfoData(info:GetBaseInfoBodyModel){
        val network: Network by inject()
        if (network.isNetworkAvailable(MainActivity.mContext)){
            val apiServices: ApiServices by inject()
            apiServices
                .getApi()
                .getBaseInfo(info)
                .enqueue(object : Callback<GetBaseInfoModel> {
                    override fun onResponse(
                        call: Call<GetBaseInfoModel>,
                        response: Response<GetBaseInfoModel>
                    ) {
                        val data = response.body()
                        if (response.isSuccessful){
                            mListener.onInvalidate(true)
                        }else{
                            mListener.onInvalidate(false)
                            Toast.makeText(MainActivity.mContext, response.toString(), Toast.LENGTH_LONG).show()
                        }
                        if (data != null){
                            mListener.onSuccess(data)
                        }
                    }

                    override fun onFailure(call: Call<GetBaseInfoModel>, t: Throwable) {
                        val showInternetErrorAlert: ShowInternetErrorAlert by inject()
                        showInternetErrorAlert.createAlert {
                            baseInfoData(info)
                        }
                        mListener.onError(t)
                    }
                })
        }else{
            val showInternetErrorAlert: ShowInternetErrorAlert by inject()
            showInternetErrorAlert.createAlert {
                baseInfoData(info)
            }
        }
    }
}