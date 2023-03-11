package com.tinyDeveloper.yavaran.api.apiModels.toGetHelp

data class GetAllToGetHelpBodyModel(
    val QueryType: String,
    val id: String? = null,
    val limit: Int = 0,
    val offset: Int = 0
)