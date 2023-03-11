package com.tinyDeveloper.yavaran.api.apiModels.users

data class InsertUserModel(
    val login: Boolean,
    val status: Boolean,
    val user: UserModel?
)