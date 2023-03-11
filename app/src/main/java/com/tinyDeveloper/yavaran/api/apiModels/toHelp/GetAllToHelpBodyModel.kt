package com.tinyDeveloper.yavaran.api.apiModels.toHelp

data class GetAllToHelpBodyModel(
    val QueryType: String,
    val id: String? = null,
    val limit:Int = 0,
    val offset:Int = 0
)