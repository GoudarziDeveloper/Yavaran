package com.tinyDeveloper.yavaran.api.apiModels.toHelp

data class GetAllToHelpModel(
    val status: Boolean,
    val toHelps: List<ToHelp>
)