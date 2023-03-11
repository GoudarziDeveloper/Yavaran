package com.tinyDeveloper.yavaran.api.apiModels.categories

data class GetAllCategoriesModel(
    val categories: List<Category>,
    val status: Boolean
)