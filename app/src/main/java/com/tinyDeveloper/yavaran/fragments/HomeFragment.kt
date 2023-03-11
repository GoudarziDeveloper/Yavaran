package com.tinyDeveloper.yavaran.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.users.ProfileInfoModel
import com.tinyDeveloper.yavaran.api.apiModels.users.UserIdAndPhoneModel
import com.tinyDeveloper.yavaran.api.presenters.users.ProfileInfoPresenter
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.databinding.HomeFragmentBinding
import com.tinyDeveloper.yavaran.publicClass.PicassoCreator
import com.tinyDeveloper.yavaran.publicClass.ChangeFragment
import org.koin.android.ext.android.inject

class HomeFragment:Fragment() {
    private var _bindHome: HomeFragmentBinding? = null
    private val bindHome get() = _bindHome!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindHome = HomeFragmentBinding.inflate(inflater, container, false)
        return bindHome.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var phone = MainActivity.mContext.phone
        if (phone != null){
            getInfo(
                UserIdAndPhoneModel(
                    "profileInfo",
                    phone
                    )
            )
        }

        bindHome.homeBtnEditInfo.setOnClickListener {
            val public: ChangeFragment by inject()
            public.newArgumentsFragment(AccountFragment(),
                isReplace = false,
                isBack = true,
                title = "ویرایش اطلاعات",
                listArguments = listOf(
                    KeyValues("type", "update"),
                    KeyValues("from", "home")
                )
            )
        }

        bindHome.homeBtnEditInfo2.setOnClickListener {
            val public: ChangeFragment by inject()
            public.newArgumentsFragment(AccountFragment(),
                isReplace = false,
                isBack = true,
                title = "ویرایش اطلاعات",
                listArguments = listOf(
                    KeyValues("type", "update"),
                    KeyValues("from", "home")
                )
            )
        }

        bindHome.homeBtnMyToGetHelps.setOnClickListener {
            val public: ChangeFragment by inject()
            public.newArgumentsFragment(
                HelpFragment(),
                isReplace = false,
                isBack = true,
                title = "درخواست های من",
                listArguments = listOf(KeyValues("type", "findUserToGetHelps"))
            )
        }

        bindHome.homeBtnMyToHelps.setOnClickListener {
            val public: ChangeFragment by inject()
            public.newArgumentsFragment(
                GivesFragment(),
                isReplace = false,
                isBack = true,
                title = "اهدایی های من",
                listArguments = listOf(KeyValues("type", "findUserToHelps"))
            )
        }

        bindHome.homeBtnExit.setOnClickListener {
            MainActivity.mContext.phone = null
            val public : ChangeFragment by inject()
            MainActivity.mContext.binding.bottomNavigationViewMain.menu.getItem(0).isChecked = true
            for (item in 0..MainActivity.mContext.supportFragmentManager.backStackEntryCount)
                MainActivity.mContext.supportFragmentManager.popBackStack()
            public.newArgumentsFragment(PhoneInputFragment(), true)
        }
    }

    fun getInfo(info: UserIdAndPhoneModel){
        ProfileInfoPresenter(object : ValidateDataListener<ProfileInfoModel> {
            override fun onSuccess(data: ProfileInfoModel) {
                if (data.status){
                    val picasso: PicassoCreator by inject()
                    picasso.getImage(MainActivity.mContext.baseDownloadUrl + data.image,null, bindHome.homeImage)
                    bindHome.homeName.text = data.name
                    bindHome.homeAllToGetHelps.text = data.allToGetHelps.toString()
                    bindHome.homeAllToHelps.text = data.allToHelps.toString()
                    bindHome.homeActiveToGetHelps.text = data.activeToGetHelps.toString()
                    bindHome.homeActiveToHelps.text = data.activeToHelps.toString()
                    bindHome.homeCompletedToGetHelps.text = data.completedToGetHelps.toString()
                    bindHome.homeCompletedToHelps.text = data.completedToHelps.toString()
                    bindHome.homeRegisteredNeeded.text = data.registeredNeeded.toString()
                    bindHome.homeAceptedNeeded.text = data.acceptedNeeded.toString()
                    bindHome.homeFailedNeeded.text = data.failedNeeded.toString()
                    bindHome.homeAllNeededToGetHelps.text = data.allNeededToGetHelps.toString()
                }else{
                    Toast.makeText(MainActivity.mContext, "موردی یافت نشد!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate){
                    Toast.makeText(MainActivity.mContext, "سرور پاسخگو نبود", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(error: Throwable) {
            }
        }).infoData(info)
    }
}