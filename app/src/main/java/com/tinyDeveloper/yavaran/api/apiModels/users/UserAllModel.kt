package com.tinyDeveloper.yavaran.api.apiModels.users

data class UserAllModel(
    val QueryType: String,
    val id: String?,
    val parentId: String?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val phone: String?,
    val nationalCode: String?,
    val address: String?,
    val longitude: String?,
    val latitude: String?,
    val postalCode: String?,
    val state: String?,
    val city: String?,
    val image: String?,
    val neededLevel: String?,
    val date: String?,
    val status: String?
)