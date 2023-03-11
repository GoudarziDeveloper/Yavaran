package com.tinyDeveloper.yavaran.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.DefaultSliderView
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.databinding.ShowToGetHelpFragmentBinding
import com.tinyDeveloper.yavaran.publicClass.*
import org.koin.android.ext.android.inject

class ShowToGetHelpFragment:Fragment() {
    private var _bindShowToGetHelp: ShowToGetHelpFragmentBinding? = null
    private val bindShowToGetHelp get() = _bindShowToGetHelp!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindShowToGetHelp = ShowToGetHelpFragmentBinding.inflate(layoutInflater, container, false)
        return bindShowToGetHelp.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null){
            val id = arguments?.getString("id")
            val parentId = arguments?.getString("parentId")
            val title = arguments?.getString("title")
            val shortDescription = arguments?.getString("shortDescription")
            val description = arguments?.getString("description")
            val category = arguments?.getString("category")
            val secretLevel = arguments?.getString("secretLevel")
            val urgency = arguments?.getString("urgency")
            val date = arguments?.getString("date")
            val status = arguments?.getString("status")
            val showDate : ShowDate by inject()
            bindShowToGetHelp.showToGetHelpTitle.text = title
            bindShowToGetHelp.showToGetHelpDate.text = "تاریخ ثبت: ${showDate.showHumanDate(date?.toLong()?: 0)}"
            val getStatusTitle : GetStatusTitle by inject()
            bindShowToGetHelp.showToGetHelpStatus.text = getStatusTitle.getStatusTitle(status)
            val getCategoryTitle : GetCategoryTitle by inject()
            bindShowToGetHelp.showToGetHelpCategory.text = category?.let {
                getCategoryTitle.getCategory(
                    it
                )
            }
            bindShowToGetHelp.showToGetHelpUrgency.text = urgency
            bindShowToGetHelp.showToGetHelpSecret.text = secretLevel
            bindShowToGetHelp.showToGetHelpShortDescription.text = shortDescription
            bindShowToGetHelp.showToGetHelpDescription.text = description
            val imageList = mutableListOf<String>()
            for (counter in 0..5){
                val image = arguments?.getString("image$counter")
                if (image != null)
                    imageList += image
            }
            if (imageList.count() > 0)
                createToHelpSlider(imageList)
            else
                bindShowToGetHelp.showToGetHelpSlider.visibility = View.GONE
            if (category != null){
                val showToGetHelpByCategory: ShowToGetHelpByCategory by inject()
                showToGetHelpByCategory.findToGetHelpByCategory(id?:"-1", category, bindShowToGetHelp)
            }

        }
    }

    private fun createToHelpSlider(imagesSlider: List<String>) {
        for (position in 0 until imagesSlider.count()) {
            val addImage = DefaultSliderView(MainActivity.mContext).apply {
                image(MainActivity.mContext.baseDownloadUrl + imagesSlider[position])
                scaleType = BaseSliderView.ScaleType.CenterCrop
                view.id = position
                bundle(Bundle())
                bundle.putInt("position", position)
            }
            bindShowToGetHelp.showToGetHelpSlider.addSlider(addImage)
        }
    }

    override fun onDestroy() {
        MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = "درخواست ها"
        MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.INVISIBLE
        super.onDestroy()
    }
}