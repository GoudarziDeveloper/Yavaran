package com.tinyDeveloper.yavaran.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.DefaultSliderView
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.databinding.ShowToHelpFragmentBinding
import com.tinyDeveloper.yavaran.publicClass.*
import org.koin.android.ext.android.inject

class ShowToHelpFragment:Fragment() {
    private var _bindShowToHelp: ShowToHelpFragmentBinding? = null
    private val bindShowToHelp get() = _bindShowToHelp!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindShowToHelp = ShowToHelpFragmentBinding.inflate(inflater, container, false)
        return bindShowToHelp.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null){
            val id = arguments?.getString("id")
            val parentId = arguments?.getString("parentId")
            val title = arguments?.getString("title")
            val shortDescription = arguments?.getString("shortDescription")
            val description = arguments?.getString("description")
            val category = arguments?.getString("category")
            val date = arguments?.getString("date")
            val status = arguments?.getString("status")
            val showDate : ShowDate by inject()
            bindShowToHelp.showToHelpTitle.text = title
            bindShowToHelp.showToHelpDate.text = "تاریخ ثبت: ${showDate.showHumanDate(date?.toLong()?: 0)}"
            val getStatusTitle : GetStatusTitle by inject()
            bindShowToHelp.showToHelpStatus.text = getStatusTitle.getStatusTitle(status)
            val getCategoryTitle : GetCategoryTitle by inject()
            bindShowToHelp.showToHelpCategory.text = category?.let { getCategoryTitle.getCategory(it) }
            bindShowToHelp.showToHelpShortDescription.text = shortDescription
            bindShowToHelp.showToHelpDescription.text = description
            val imageList = mutableListOf<String>()
            for (counter in 0..5){
                val image = arguments?.getString("image$counter")
                if (image != null)
                    imageList += image
            }
            if (imageList.count() > 0)
                createToHelpSlider(imageList)
            else
                bindShowToHelp.showToHelpSlider.visibility = View.GONE
            if (category != null){
                val showToHelpByCategory : ShowToHelpByCategory by inject()
                showToHelpByCategory.getToHelpsByCategoryId(id?:"-1", category, bindShowToHelp)
            }
        }
    }

    private fun createToHelpSlider(imagesSlider: MutableList<String>) {
        for (position in 0 until imagesSlider.count()) {
            val addImage = DefaultSliderView(MainActivity.mContext).apply {
                image(MainActivity.mContext.baseDownloadUrl + imagesSlider[position])
                scaleType = BaseSliderView.ScaleType.CenterCrop
                view.id = position
                bundle(Bundle())
                bundle.putInt("position", position)
            }
            bindShowToHelp.showToHelpSlider.addSlider(addImage)
        }
    }
    override fun onDestroy() {
        MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = "اهدایی ها"
        MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.INVISIBLE
        super.onDestroy()
    }
}

