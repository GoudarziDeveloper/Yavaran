package com.tinyDeveloper.yavaran.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.adapter.ToGetHelpAdapter
import com.tinyDeveloper.yavaran.adapter.ToHelpAdapter
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.toGetHelp.GetAllToGetHelpModel
import com.tinyDeveloper.yavaran.api.apiModels.toHelp.GetAllToHelpModel
import com.tinyDeveloper.yavaran.api.presenters.categories.FindToGetHelpByCategoryPresenter
import com.tinyDeveloper.yavaran.api.presenters.categories.FindToHelpByCategoryPresenter
import com.tinyDeveloper.yavaran.databinding.CategoryFragmentBinding

class CategoryFragment: Fragment() {
    private var _bindCategory : CategoryFragmentBinding? = null
    private val bindCategory get() = _bindCategory!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindCategory = CategoryFragmentBinding.inflate(inflater, container, false)
        return bindCategory.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString("id")
        val isToGetHelp = arguments?.getString("isToGetHelp")
        if (id != null)
            if (isToGetHelp == "true")
                findToGetHelpByCategory(id)
            else
                findToHelpByCategory(id)
    }

    fun findToGetHelpByCategory(id: String){
        FindToGetHelpByCategoryPresenter(object : ValidateDataListener<GetAllToGetHelpModel> {
            @SuppressLint("WrongConstant")
            override fun onSuccess(data: GetAllToGetHelpModel) {
                val adapter = ToGetHelpAdapter(data.toGetHelps)
                bindCategory.categoryList.layoutManager =
                    GridLayoutManager(context,1 , LinearLayout.VERTICAL, false)
                bindCategory.categoryList.adapter = adapter
                bindCategory.categoryLoadPB.visibility = View.INVISIBLE
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate)
                    Toast.makeText(MainActivity.mContext, "noValid", Toast.LENGTH_LONG).show()
                bindCategory.categoryLoadPB.visibility = View.INVISIBLE
            }

            override fun onError(error: Throwable) {
                bindCategory.categoryLoadPB.visibility = View.INVISIBLE
            }
        }).categoriesData("findToGetHelp", id)
    }

    fun findToHelpByCategory(id:String){
        FindToHelpByCategoryPresenter(object : ValidateDataListener<GetAllToHelpModel> {
            @SuppressLint("WrongConstant")
            override fun onSuccess(data: GetAllToHelpModel) {
                val adapter = ToHelpAdapter(data.toHelps)
                bindCategory.categoryList.layoutManager =
                    GridLayoutManager(context,1 , LinearLayout.VERTICAL, false)
                bindCategory.categoryList.adapter = adapter
                bindCategory.categoryLoadPB.visibility = View.INVISIBLE
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate)
                    Toast.makeText(MainActivity.mContext, "noValid", Toast.LENGTH_LONG).show()
                bindCategory.categoryLoadPB.visibility = View.INVISIBLE
            }

            override fun onError(error: Throwable) {
                bindCategory.categoryLoadPB.visibility = View.INVISIBLE
            }
        }).categoriesData("findToHelp", id)
    }

    override fun onDestroy() {
        MainActivity.mContext.binding.includeToolbar.toolbarBack.visibility = View.INVISIBLE
        MainActivity.mContext.binding.includeToolbar.toolbarTitle.text = "درخواست ها"
        super.onDestroy()
    }
}