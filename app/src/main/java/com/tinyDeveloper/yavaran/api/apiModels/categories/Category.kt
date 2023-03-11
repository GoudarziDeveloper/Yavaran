package com.tinyDeveloper.yavaran.api.apiModels.categories

data class Category(
    val description: String,
    val id: String,
    val image: String,
    val parentId: String,
    val status: String,
    val title: String
)