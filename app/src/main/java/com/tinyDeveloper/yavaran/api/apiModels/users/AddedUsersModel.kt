package com.tinyDeveloper.yavaran.api.apiModels.users

data class AddedUsersModel(
    val status: Boolean,
    val users: List<UserModel>?
)