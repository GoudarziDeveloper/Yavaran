package com.tinyDeveloper.yavaran.api

interface ValidateDataListener<N> {
    fun onSuccess(data: N)
    fun onInvalidate(isValidate:Boolean)
    fun onError(error:Throwable)
}