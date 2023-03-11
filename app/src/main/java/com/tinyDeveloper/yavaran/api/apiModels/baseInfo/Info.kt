package com.tinyDeveloper.yavaran.api.apiModels.baseInfo

data class Info(
    val appEnabled: Char,
    val categoriesUrl: String,
    val checkCodeUrl: String,
    val id: String,
    val lastVersion: String,
    val updateTitle: String,
    val updateText: String,
    val updateLink: String,
    val messageEnabled: Char,
    val messageText: String,
    val messageTitle: String,
    val minVersion: String,
    val sendCodeUrl: String,
    val status: String,
    val stopText: String,
    val stopTitle: String,
    val toGetHelpsUrl: String,
    val toHelpsUrl: String,
    val uploadUrl: String,
    val usersUrl: String,
    val baseUrl: String,
    val messageImages: List<String>
)