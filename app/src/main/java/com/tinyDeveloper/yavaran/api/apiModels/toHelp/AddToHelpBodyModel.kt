package com.tinyDeveloper.yavaran.api.apiModels.toHelp

data class AddToHelpBodyModel(
    val QueryType: String,
    val id: String,
    val parentId: String?,
    val title: String,
    val shortDescription: String,
    val description: String,
    val category: String,
    val date: String,
    val status: String,
    val urls: List<String>?
)