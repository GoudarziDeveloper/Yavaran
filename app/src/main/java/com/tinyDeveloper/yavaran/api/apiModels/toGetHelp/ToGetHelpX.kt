package com.tinyDeveloper.yavaran.api.apiModels.toGetHelp

data class ToGetHelpX(
    val category: String,
    val date: String,
    val description: String,
    val id: String,
    val parentId: String,
    val secretLevel: String,
    val shortDescription: String,
    val status: String,
    val title: String,
    val urgency: String
)