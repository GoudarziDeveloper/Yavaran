package com.tinyDeveloper.yavaran.api.apiModels.toHelp

data class AddToHelpModel(
    val status: Boolean,
    val toHelp: ToHelpXX,
    val images: List<String>
)