package com.tinyDeveloper.yavaran.publicClass

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class ShowDate {
    @SuppressLint("SimpleDateFormat")
    fun showHumanDate(longDate: Long): String {
        val simple = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val result = Date(longDate)
        return simple.format(result)
    }
}