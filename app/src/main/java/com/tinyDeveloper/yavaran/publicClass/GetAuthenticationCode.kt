package com.tinyDeveloper.yavaran.publicClass

import android.widget.Toast
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.code.CodeApiModel
import com.tinyDeveloper.yavaran.api.presenters.code.GetAuthenticationPresenter
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.fragments.CodeInputFragment
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class GetAuthenticationCode: KoinComponent {
    fun getCode(mobile:String, goToCodeFragment: Boolean = false) {
        GetAuthenticationPresenter(object : ValidateDataListener<CodeApiModel> {
            override fun onSuccess(data: CodeApiModel) {
                if (data.Code == "null")
                    Toast.makeText(MainActivity.mContext, "خطا در ارسال شماره", Toast.LENGTH_LONG).show()
                else if (data.Code > "2000") {
                    Toast.makeText(MainActivity.mContext, "کد برای شما ارسال شد", Toast.LENGTH_LONG).show()
                    if (goToCodeFragment) {
                        val public: ChangeFragment by inject()
                        public.newArgumentsFragment(CodeInputFragment(),
                            isReplace = true,
                            listArguments = listOf(
                                KeyValues("mobile", mobile)
                            )
                        )
                    }
                } else {
                    Toast.makeText(MainActivity.mContext, "لطفا بعدا دوباره امتحان کنید", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onInvalidate(isValidate: Boolean) {
            }

            override fun onError(error: Throwable) {
                getCode(mobile)
            }
        }).authenticationData(mobile)
    }
}