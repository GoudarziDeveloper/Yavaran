package com.tinyDeveloper.yavaran.api.presenters.categories

import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ApiServices
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.categories.CategoriesBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.categories.GetAllCategoriesModel
import com.tinyDeveloper.yavaran.publicClass.Network
import com.tinyDeveloper.yavaran.publicClass.ShowInternetErrorAlert
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Response

class GetAllCategoriesPresenter(val mListener:ValidateDataListener<GetAllCategoriesModel>): KoinComponent {
    fun categoriesData(QueryType:String){
        val network: Network by inject()
        if (network.isNetworkAvailable(MainActivity.mContext)){
            val apiServices : ApiServices by inject()
            apiServices
                .getApi()
                .getAllCategories(MainActivity.mContext.categoriesUrl, CategoriesBodyModel(QueryType))
                .enqueue(object : retrofit2.Callback<GetAllCategoriesModel>{
                    override fun onResponse(call: Call<GetAllCategoriesModel>, response: Response<GetAllCategoriesModel>) {
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
                    override fun onFailure(call: Call<GetAllCategoriesModel>, t: Throwable) {
                        val showInternetErrorAlert: ShowInternetErrorAlert by inject()
                        showInternetErrorAlert.createAlert {
                            categoriesData(QueryType)
                        }
                        mListener.onError(t)
                    }
                })
        }else{
            val showInternetErrorAlert: ShowInternetErrorAlert by inject()
            showInternetErrorAlert.createAlert {
                categoriesData(QueryType)
            }
        }
    }
}