package com.tinyDeveloper.yavaran.publicClass

import android.graphics.Bitmap
import android.util.Base64
import android.widget.Toast
import androidx.core.content.edit
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.users.InsertUserModel
import com.tinyDeveloper.yavaran.api.apiModels.users.UserAllModel
import com.tinyDeveloper.yavaran.api.presenters.users.InsertUserPresenter
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.fragments.HelpFragment
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.io.ByteArrayOutputStream

class RegisterUser: KoinComponent {
    fun insert(
        info: UserAllModel
    ){
        InsertUserPresenter(object : ValidateDataListener<InsertUserModel> {
            override fun onSuccess(data: InsertUserModel) {
                if (data.status && data.user != null){
                    val public: ChangeFragment by inject()
                    public.newArgumentsFragment(HelpFragment(),
                        isReplace = true,
                        listArguments = listOf(KeyValues("type", "help")),
                        title = "درخواست ها"
                    )
                    Toast.makeText(MainActivity.mContext, "ثبت نام شما انجام شد", Toast.LENGTH_SHORT).show()
                    MainActivity.mContext.phone = info.phone
                    MainActivity.mContext.info.edit {
                        putString("mobile", info.phone)
                    }
                }
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate)
                    Toast.makeText(MainActivity.mContext, "خطایی رخ داده است دوباره تلاش کنید", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Throwable) {
            }
        }).insertNewUser(info)
    }

    fun encodeToBase64(image: Bitmap?): String? {
        if (image != null){
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b: ByteArray = baos.toByteArray()
            return Base64.encodeToString(b, Base64.DEFAULT)
        }
        return null
    }
}