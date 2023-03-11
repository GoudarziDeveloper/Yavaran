package com.tinyDeveloper.yavaran.api.apiModels.baseInfo

import com.tinyDeveloper.yavaran.api.apiModels.categories.Category

data class GetBaseInfoModel(
    val status: Boolean,
    val info: Info?,
    val categories: List<Category>?
)