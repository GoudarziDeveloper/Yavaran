package com.tinyDeveloper.yavaran.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.adapter.UsersAdapter
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.users.AddedUsersModel
import com.tinyDeveloper.yavaran.api.apiModels.users.UserIdAndPhoneModel
import com.tinyDeveloper.yavaran.api.presenters.users.AddedUsersPresenter
import com.tinyDeveloper.yavaran.dataModels.KeyValues
import com.tinyDeveloper.yavaran.databinding.NeededFragmentBinding
import com.tinyDeveloper.yavaran.publicClass.ChangeFragment
import org.koin.android.ext.android.inject

class NeededFragment: Fragment() {
    private var _bindAddNeeded : NeededFragmentBinding? = null
    private val bindAddNeeded get() = _bindAddNeeded!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindAddNeeded = NeededFragmentBinding.inflate(layoutInflater, container, false)
        return bindAddNeeded.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var phone = MainActivity.mContext.phone
        if (phone != null){
            getNeeded(
                UserIdAndPhoneModel(
                    "findAddedUser",
                    phone
                    )
            )
        }
        bindAddNeeded.neededBtnAdd.setOnClickListener {
            val public: ChangeFragment by inject()
            public.newArgumentsFragment(AccountFragment(),
                isReplace = false,
                isBack = true,
                title = "افزودن نیازمند",
                listArguments = listOf(
                    KeyValues("type", "insert"),
                    KeyValues("from", "needed")
                )
            )
        }
    }

    fun getNeeded(info:UserIdAndPhoneModel){
        AddedUsersPresenter(object : ValidateDataListener<AddedUsersModel> {
            override fun onSuccess(data: AddedUsersModel) {
                if (data.status && data.users != null){
                    val adapter = UsersAdapter(data.users)
                    bindAddNeeded.neededList.layoutManager =
                        LinearLayoutManager(MainActivity.mContext)
                    bindAddNeeded.neededList.adapter = adapter
                }else
                    Toast.makeText(MainActivity.mContext, "موردی یافت نشد!", Toast.LENGTH_SHORT).show()
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate){
                    Toast.makeText(MainActivity.mContext, "سرور پاسخگو نبود", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(error: Throwable) {
            }
        }).addedUsersData(info)
    }
}