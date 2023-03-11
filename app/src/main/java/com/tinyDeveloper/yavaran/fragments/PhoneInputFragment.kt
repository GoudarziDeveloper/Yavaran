package com.tinyDeveloper.yavaran.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.databinding.PhoneInputFragmentBinding
import com.tinyDeveloper.yavaran.publicClass.GetAuthenticationCode
import com.tinyDeveloper.yavaran.publicClass.ChangeFragment
import com.tinyDeveloper.yavaran.publicClass.Network
import com.tinyDeveloper.yavaran.publicClass.ShowDate
import org.koin.android.ext.android.inject

class PhoneInputFragment: Fragment() {
    private var _bindPhoneInput : PhoneInputFragmentBinding? = null
    private val bindPhoneInput get() = _bindPhoneInput!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindPhoneInput = PhoneInputFragmentBinding.inflate(layoutInflater, container, false)
        return bindPhoneInput.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.mContext.binding.bottomNavigationViewMain.visibility = View.INVISIBLE
        MainActivity.mContext.binding.includeToolbar.toolbarMain.visibility = View.INVISIBLE
        bindPhoneInput.phoneNextBtn.setOnClickListener {
            bindPhoneInput.phoneNumberInput.background = ContextCompat.getDrawable(requireContext(), R.drawable.edit_text_shape)
            bindPhoneInput.textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            if (
                bindPhoneInput.phoneNumberInput.text.toString().count() == 11 &&
                bindPhoneInput.phoneNumberInput.text.toString()[0] == '0' &&
                bindPhoneInput.phoneNumberInput.text.toString()[1] == '9'
            ){
                bindPhoneInput.textView.text = "درحال ارسال..."
                val getAuthenticationCode: GetAuthenticationCode by inject()
                getAuthenticationCode.getCode(bindPhoneInput.phoneNumberInput.text.toString(), true)
            }else{
                bindPhoneInput.phoneNumberInput.background = ContextCompat.getDrawable(requireContext(), R.drawable.edit_text_error_shap)
                bindPhoneInput.textView.text = "یک شماره تماس صحیح وارد کنید"
                bindPhoneInput.textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
        }
    }
}