package com.tinyDeveloper.yavaran.api.apiModels.users

data class ProfileInfoModel(
    val status:Boolean,
    val acceptedNeeded: Int,
    val activeToGetHelps: Int,
    val activeToHelps: Int,
    val allNeededToGetHelps: Int,
    val allToGetHelps: Int,
    val allToHelps: Int,
    val completedToGetHelps: Int,
    val completedToHelps: Int,
    val failedNeeded: Int,
    val image: String,
    val name: String,
    val registeredNeeded: Int
)