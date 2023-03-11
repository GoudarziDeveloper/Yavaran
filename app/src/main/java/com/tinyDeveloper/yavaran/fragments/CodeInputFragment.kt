package com.tinyDeveloper.yavaran.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.tinyDeveloper.yavaran.R
import com.tinyDeveloper.yavaran.databinding.CodeInputFragmentBinding
import com.tinyDeveloper.yavaran.publicClass.CheckAuthenticationCode
import com.tinyDeveloper.yavaran.publicClass.GetAuthenticationCode
import com.tinyDeveloper.yavaran.publicClass.ChangeFragment
import org.koin.android.ext.android.inject

class CodeInputFragment: Fragment() {
    private var _bindCodeInput : CodeInputFragmentBinding? = null
    private val bindCodeInput get() = _bindCodeInput!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindCodeInput = CodeInputFragmentBinding.inflate(layoutInflater, container, false)
        return bindCodeInput.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val public : ChangeFragment by inject()

        val mobile = arguments?.getString("mobile", null)
        timer(mobile)

        bindCodeInput.codeChangeMobile.setOnClickListener {
            public.newArgumentsFragment(PhoneInputFragment(), true)
        }

        bindCodeInput.codeNext.setOnClickListener {
            if (
                bindCodeInput.codeInput.text.toString().count() in 4..9
            ) {

                bindCodeInput.codeInput.background = ContextCompat.getDrawable(requireContext(),
                    R.drawable.edit_text_shape)

                bindCodeInput.codeHelp.text = "لطفا کمی صبر کنید..."
                bindCodeInput.codeHelp.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                if (mobile != null) {
                    val checkCode: CheckAuthenticationCode by inject()
                    checkCode.checkCode(
                        mobile,
                        bindCodeInput.codeInput.text.toString(),
                        bindCodeInput
                    )
                }else
                    Toast.makeText(context, "شماره گم شد", Toast.LENGTH_LONG).show()
            }else{
                bindCodeInput.codeInput.background = ContextCompat.getDrawable(requireContext(),
                    R.drawable.edit_text_error_shap)
                bindCodeInput.codeHelp.text = "کد نادرست است"
                bindCodeInput.codeHelp.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
        }
    }

    private fun timer(mobile:String?){
        object : CountDownTimer(120000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                bindCodeInput.codeAgain.text = "${millisUntilFinished / 60000}:${(millisUntilFinished % 60000) / 1000}"
            }
            override fun onFinish() {
                bindCodeInput.codeAgain.text = "ارسال مجدد"
                if (mobile != null){
                    bindCodeInput.codeAgain.setOnClickListener {
                        val getCode : GetAuthenticationCode by inject()
                        getCode.getCode(mobile)
                        timer(mobile)
                    }
                }
            }
        }.start()
    }
}