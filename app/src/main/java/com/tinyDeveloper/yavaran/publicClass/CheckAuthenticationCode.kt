package com.tinyDeveloper.yavaran.publicClass

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.code.CodeApiModel
import com.tinyDeveloper.yavaran.api.apiModels.users.FindAndGetUserModel
import com.tinyDeveloper.yavaran.api.apiModels.users.UserIdAndPhoneModel
import com.tinyDeveloper.yavaran.api.presenters.code.CheckAuthenticationPresenter
import com.tinyDeveloper.yavaran.api.presenters.users.FindAndGetUsersPresenter
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.databinding.CodeInputFragmentBinding
import com.tinyDeveloper.yavaran.fragments.HelpFragment
import com.tinyDeveloper.yavaran.fragments.RegisterUserFragment
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class CheckAuthenticationCode : KoinComponent {
    fun checkCode(mobile:String, code:String, bindCodeInput:CodeInputFragmentBinding? = null) {
        CheckAuthenticationPresenter(object : ValidateDataListener<CodeApiModel> {
            override fun onSuccess(data: CodeApiModel) {
                if (data.Code == "true") {
                    if (bindCodeInput != null){
                        find(UserIdAndPhoneModel("find", mobile))
                    }
                } else {
                    if (bindCodeInput != null){
                        bindCodeInput.codeHelp.text = "کد اشتباه است!"
                        bindCodeInput.codeHelp.setTextColor(ContextCompat.getColor(MainActivity.mContext, R.color.red))

                        bindCodeInput.codeInput.background = ContextCompat.getDrawable(MainActivity.mContext, R.drawable.edit_text_error_shap)
                    }
                }
            }

            override fun onInvalidate(isValidate: Boolean) {
            }

            override fun onError(error: Throwable) {
                checkCode(mobile, code)
            }
        }).authenticationData(mobile, code)
    }
    fun find(info: UserIdAndPhoneModel){
        FindAndGetUsersPresenter(object : ValidateDataListener<FindAndGetUserModel> {
            override fun onSuccess(data: FindAndGetUserModel) {
                val public: ChangeFragment by inject()
                if (!data.status || data.user == null){
                    public.newArgumentsFragment(RegisterUserFragment(),
                        isReplace = true,
                        title = null,
                        listArguments = listOf(KeyValues("mobile", info.phone!!))
                    )
                }else{
                    public.newArgumentsFragment(HelpFragment(),
                        isReplace = true,
                        title = "درخواست ها",
                        listArguments = listOf(KeyValues("type", "help"))
                    )
                    MainActivity.mContext.phone = info.phone
                    MainActivity.mContext.info.edit {
                        putString("mobile", info.phone)
                    }
                }
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate)
                    Toast.makeText(MainActivity.mContext,"مشکلی رخ داده است!", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Throwable) {
            }
        }).findOrGetUser(info)
    }
}