package com.tinyDeveloper.yavaran.api.apiModels.toGetHelp

data class AddToGetHelpBodyModel(
    val QueryType: String,
    val id: String?,
    val parentId: String,
    val title: String,
    val shortDescription: String,
    val description: String,
    val category: String,
    val secretLevel: String,
    val urgency: String,
    val urls: List<String>?,
    val date: String,
    val status: String
)