package com.tinyDeveloper.yavaran.api.apiModels.toGetHelp

data class AddToGetHelpModel(
    val images: List<String>,
    val status: Boolean,
    val toGetHelp: ToGetHelpXX
)