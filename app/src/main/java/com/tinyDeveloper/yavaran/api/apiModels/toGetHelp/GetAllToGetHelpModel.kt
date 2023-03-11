package com.tinyDeveloper.yavaran.api.apiModels.toGetHelp

data class GetAllToGetHelpModel(
    val status: Boolean,
    val toGetHelps: List<ToGetHelp>
)