package com.tinyDeveloper.yavaran.publicClass

import com.tinyDeveloper.yavaran.MainActivity

class GetCategoryTitle {
    fun getCategory(categoryId: String): String{
        for (item in MainActivity.mContext.categoriesList){
            if (categoryId == item.id){
                return item.title
            }
        }
        return "نامشخص"
    }
}