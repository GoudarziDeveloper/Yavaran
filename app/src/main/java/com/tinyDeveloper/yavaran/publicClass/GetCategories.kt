package com.tinyDeveloper.yavaran.publicClass

import android.widget.Toast
import com.tinyDeveloper.yavaran.MainActivity
import com.tinyDeveloper.yavaran.api.ValidateDataListener
import com.tinyDeveloper.yavaran.api.apiModels.categories.GetAllCategoriesModel
import com.tinyDeveloper.yavaran.api.presenters.categories.GetAllCategoriesPresenter

class GetCategories {
    fun getCategories(){
        GetAllCategoriesPresenter(object : ValidateDataListener<GetAllCategoriesModel> {
            override fun onSuccess(data: GetAllCategoriesModel) {
                MainActivity.mContext.categoriesList += data.categories
            }

            override fun onInvalidate(isValidate: Boolean) {
                if (!isValidate)
                    Toast.makeText(MainActivity.mContext, "noValid", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Throwable) {
            }
        }).categoriesData("getAll")
    }
}