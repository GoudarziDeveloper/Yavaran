package com.tinyDeveloper.yavaran.api.presenters.users

import android.view.View
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ApiServices
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.users.AddedUsersModel
import com.tinyDeveloper.yavaran.api.apiModels.users.UserIdAndPhoneModel
import com.tinyDeveloper.yavaran.publicClass.Network
import com.tinyDeveloper.yavaran.publicClass.ShowInternetErrorAlert
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddedUsersPresenter(val mListener:ValidateDataListener<AddedUsersModel>):KoinComponent {
    fun addedUsersData(info:UserIdAndPhoneModel){
        val network: Network by inject()
        if (network.isNetworkAvailable(MainActivity.mContext)){
            MainActivity.mContext.binding.mainStayView.visibility = View.VISIBLE
            MainActivity.mContext.binding.mainStayText.visibility = View.VISIBLE
            MainActivity.mContext.binding.mainStayPB.visibility = View.VISIBLE
            val apiServices : ApiServices by inject()
            apiServices
                .getApi()
                .addedUsers(MainActivity.mContext.usersUrl, info)
                .enqueue(object : Callback<AddedUsersModel> {
                    override fun onResponse(
                        call: Call<AddedUsersModel>,
                        response: Response<AddedUsersModel>
                    ) {
                        MainActivity.mContext.binding.mainStayView.visibility = View.INVISIBLE
                        MainActivity.mContext.binding.mainStayText.visibility = View.INVISIBLE
                        MainActivity.mContext.binding.mainStayPB.visibility = View.INVISIBLE
                        val data = response.body()
                        if (response.isSuccessful){
                            mListener.onInvalidate(true)
                        }
                        else{
                            mListener.onInvalidate(false)
                        }
                        if (data != null){
                            mListener.onSuccess(data)
                        }
                    }

                    override fun onFailure(call: Call<AddedUsersModel>, t: Throwable) {
                        MainActivity.mContext.binding.mainStayView.visibility = View.INVISIBLE
                        MainActivity.mContext.binding.mainStayText.visibility = View.INVISIBLE
                        MainActivity.mContext.binding.mainStayPB.visibility = View.INVISIBLE
                        val showInternetErrorAlert: ShowInternetErrorAlert by inject()
                        showInternetErrorAlert.createAlert {
                            addedUsersData(info)
                        }
                        mListener.onError(t)
                    }
                })
        }else{
            val showInternetErrorAlert: ShowInternetErrorAlert by inject()
            showInternetErrorAlert.createAlert {
                addedUsersData(info)
            }
        }
    }
}