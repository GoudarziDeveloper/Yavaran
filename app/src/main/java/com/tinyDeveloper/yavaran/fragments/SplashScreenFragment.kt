package com.tinyDeveloper.yavaran.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.tinyDeveloper.yavaran.BuildConfig
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.baseInfo.GetBaseInfoBodyModel
import com.tinyDeveloper.yavaran.api.apiModels.baseInfo.GetBaseInfoModel
import com.tinyDeveloper.yavaran.api.apiModels.categories.Category
import com.tinyDeveloper.yavaran.api.presenters.baseInfo.GetBaseInfoPresenter
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.databinding.SplashScreenFragmentBinding
import com.tinyDeveloper.yavaran.publicClass.*
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment: Fragment() {
    private var _bindSplashScreen : SplashScreenFragmentBinding? = null
    private val bindSplashScreen get() = _bindSplashScreen!!
    private var isLoaded = false
    private var isTimeOut = false
    private var isCheckedBaseInfo = false
    private var dataHold: GetBaseInfoModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindSplashScreen = SplashScreenFragmentBinding.inflate(inflater, container, false)
        return bindSplashScreen.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBaseInfo(GetBaseInfoBodyModel("getBaseInfo"))

        val animZoom = AnimationUtils.loadAnimation(requireContext() , R.anim.zoom_in)
        //val animWelcome = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_move)

        bindSplashScreen.splashLogoImage.animation = animZoom
        bindSplashScreen.splashDescriptionText.animation = animZoom

        object : CountDownTimer(3000,1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                isTimeOut = true
                if (!isCheckedBaseInfo && dataHold != null){
                    checkBaseInfo(dataHold!!)
                    isCheckedBaseInfo = true
                }
                load()
            }
        }.start()
    }

    private fun getBaseInfo(info: GetBaseInfoBodyModel){
        GetBaseInfoPresenter(object : ValidateDataListener<GetBaseInfoModel> {
            override fun onSuccess(data: GetBaseInfoModel) {
                dataHold = data
                if (isTimeOut){
                    checkBaseInfo(data)
                    isCheckedBaseInfo = true
                }
                if (isTimeOut)
                    load()
            }

            override fun onInvalidate(isValidate: Boolean) {

            }

            override fun onError(error: Throwable) {
                Toast.makeText(MainActivity.mContext, error.toString(), Toast.LENGTH_LONG).show()
            }
        }).baseInfoData(info)
    }

    private fun load(){
        if (isLoaded){
            val public: ChangeFragment by inject()
            if (MainActivity.mContext.phone == null){
                public.newArgumentsFragment(PhoneInputFragment(), true)
            }else{
                public.newArgumentsFragment(
                    HelpFragment(),
                    isReplace = true,
                    title = "درخواست ها",
                    listArguments = listOf(KeyValues("type", "help"))
                )
            }
        }
    }

    private fun checkBaseInfo(data:GetBaseInfoModel){
        if (data.status){
            if (data.info != null){
                if (data.info.appEnabled == '1'){
                    MainActivity.mContext.baseUrl = data.info.baseUrl
                    MainActivity.mContext.baseDownloadUrl = data.info.uploadUrl
                    MainActivity.mContext.usersUrl = data.info.usersUrl
                    MainActivity.mContext.checkCodeUrl = data.info.checkCodeUrl
                    MainActivity.mContext.sendCodeUrl = data.info.sendCodeUrl
                    MainActivity.mContext.toGetHelpUrl = data.info.toGetHelpsUrl
                    MainActivity.mContext.toHelpUrl = data.info.toHelpsUrl
                    MainActivity.mContext.categoriesUrl = data.info.categoriesUrl
                    MainActivity.mContext.categoriesList = data.categories as MutableList<Category>

                    isLoaded = true


                    if (data.info.messageEnabled == '1'){
                        val showMessageAlert: ShowMessageAlert by inject()
                        showMessageAlert.createAlert(data.info.messageImages.reversed(), data.info.messageTitle, data.info.messageText)
                    }

                    val appVersion = BuildConfig.VERSION_CODE
                    if (appVersion < data.info.lastVersion.toInt() && data.info.minVersion.toInt() <= appVersion){
                        val showUpdateAlert: ShowUpdateAlert by inject()
                        showUpdateAlert.createAlert(data.info.updateTitle, data.info.updateText){
                            val webIntent: Intent = Uri.parse(data.info.updateLink).let { webpage ->
                                Intent(Intent.ACTION_VIEW, webpage)
                            }
                            MainActivity.mContext.startActivity(webIntent)
                            MainActivity.mContext.finishAffinity()
                        }
                    }else if (data.info.minVersion.toInt() > appVersion){
                        isLoaded = false
                        val showDisableAppAlert: ShowDisableAppAlert by inject()
                        showDisableAppAlert.createAlert(
                            "برنامه نیاز به بروزرسانی دارد",
                            "لطفا برنامه را بروزرسانی کنید این نسخه از برنامه دیگر توسط توسعه دهنده پشتیبانی نمیشود",
                            "بروزرسانی",
                            ContextCompat.getDrawable(MainActivity.mContext, R.drawable.new_vesion)
                        ) {
                            val webIntent: Intent = Uri.parse(data.info.updateLink).let { webpage ->
                                Intent(Intent.ACTION_VIEW, webpage)
                            }
                            MainActivity.mContext.startActivity(webIntent)
                            MainActivity.mContext.finishAffinity()
                        }
                    }
                }else{
                    val showDisableAppAlert: ShowDisableAppAlert by inject()
                    showDisableAppAlert.createAlert(
                        data.info.stopTitle,
                        data.info.stopText,
                        "بعدا میام!",
                        ContextCompat.getDrawable(MainActivity.mContext, R.drawable.no_internet_icon)
                    ) {
                        MainActivity.mContext.finishAffinity()
                    }
                }
            }else{
                val showDisableAppAlert: ShowDisableAppAlert by inject()
                showDisableAppAlert.createAlert(
                    "مشکلی رخ داده است",
                    "مشکلی از سمت سرور پیش امده ولی نگران نباشید تقصیر شما نیست",
                    "تلاش مجدد!",
                    ContextCompat.getDrawable(MainActivity.mContext, R.drawable.no_internet_icon)
                ) {
                    getBaseInfo(GetBaseInfoBodyModel("getBaseInfo"))
                }
            }
        }else{
            val showDisableAppAlert: ShowDisableAppAlert by inject()
            showDisableAppAlert.createAlert(
                "مشکلی رخ داده است",
                "مشکلی از سمت سرور پیش امده ولی نگران نباشید تقصیر شما نیست",
                "تلاش مجدد!",
                ContextCompat.getDrawable(MainActivity.mContext, R.drawable.no_internet_icon)
            ) {
                getBaseInfo(GetBaseInfoBodyModel("getBaseInfo"))
            }
        }
    }
}