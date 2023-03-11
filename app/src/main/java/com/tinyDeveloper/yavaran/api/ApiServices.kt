package com.tinyDeveloper.yavaran.api

import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.apiModels.baseInfo.GetBaseInfoBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.baseInfo.GetBaseInfoModel
import com.tinyDeveloper.yavaran.api.apiModels.categories.CategoriesBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.categories.GetAllCategoriesModel
import com.tinyDeveloper.yavaran.api.apiModels.code.CheckCodeModel
import com.tinyDeveloper.yavaran.api.apiModels.code.MobileApiModel
import com.tinyDeveloper.yavaran.api.apiModels.code.CodeApiModel
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.*
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.*
import com.tinyDeveloper.yavaran.api.apiModels.users.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

class ApiServices {
    interface ApiService {
        //TODO *************** BaseInfo ***************
        @POST("help/api/v1/baseInfoPresenter.php")
        fun getBaseInfo(@Body getBaseInfoBodyModel: GetBaseInfoBodyModel): Call<GetBaseInfoModel>

        //TODO *************** Code ***************
        @POST
        fun getAuthenticationCode(@Url url: String, @Body mobile: MobileApiModel): Call<CodeApiModel>

        @POST
        fun checkAuthenticationCode(@Url url: String, @Body mobile: CheckCodeModel): Call<CodeApiModel>

        //TODO *************** User ***************
        @POST
        fun insertUser(@Url url: String, @Body userInfo: UserAllModel): Call<InsertUserModel>

        @POST
        fun updateAndDeleteUser(@Url url: String, @Body userInfo: UserAllModel): Call<UpdateAndDeleteUserModel>

        @POST
        fun findAndGetUser(@Url url: String, @Body userInfo: UserIdAndPhoneModel): Call<FindAndGetUserModel>

        @POST
        fun deleteUser(@Url url: String, @Body userInfo: UserIdAndPhoneModel): Call<UpdateAndDeleteUserModel>

        @POST
        fun addedUsers(@Url url: String, @Body userInfo: UserIdAndPhoneModel): Call<AddedUsersModel>

        @POST
        fun getUserInfo(@Url url: String, @Body userInfo: UserIdAndPhoneModel): Call<ProfileInfoModel>

        //TODO ************* Categories *************
        @POST
        fun getAllCategories(@Url url: String, @Body userInfo: CategoriesBodyModel): Call<GetAllCategoriesModel>

        @POST
        fun findToGetHelpByCategories(@Url url: String, @Body userInfo: CategoriesBodyModel): Call<GetAllToGetHelpModel>

        @POST
        fun findToHelpByCategories(@Url url: String, @Body userInfo: CategoriesBodyModel): Call<GetAllToHelpModel>

        //TODO ************* ToGetHelp *************
        @POST
        fun getAllToGetHelp(@Url url: String, @Body userInfo: GetAllToGetHelpBodyModel): Call<GetAllToGetHelpModel>

        @POST
        fun addToGetHelp(@Url url: String, @Body userInfo: AddToGetHelpBodyModel): Call<AddToGetHelpModel>

        @POST
        fun updateToGetHelp(@Url url: String, @Body userInfo: AddToGetHelpBodyModel): Call<UpdateAndDeleteToGetHelpModel>

        //TODO ************* ToHelp *************
        @POST
        fun getAllToHelp(@Url url: String, @Body userInfo: GetAllToHelpBodyModel): Call<GetAllToHelpModel>

        @POST
        fun addToHelp(@Url url: String, @Body userInfo: AddToHelpBodyModel): Call<AddToHelpModel>

        @POST
        fun updateToHelp(@Url url: String, @Body userInfo: AddToHelpBodyModel): Call<UpdateAndDeleteToHelpModel>
    }
    fun getApi() : ApiService =
        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MainActivity.mContext.baseUrl)
                .client(
                    OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.MINUTES)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(20, TimeUnit.SECONDS)
                        .build())
                .build().create(ApiService::class.java)
}