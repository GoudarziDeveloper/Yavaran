package com.tinyDeveloper.yavaran.publicClass

class GetStatusTitle {
    fun getStatusTitle(status: String?): String{
        when(status){
            "active"-> return "فعال"
            "canceled"-> return "لغو شده"
            "solved"-> return "دریافت شده"
            "blocked"-> return "مسدود شده"
            "hold"-> return "منتظر تایید"
            "completed"-> return "تکمیل شده"
        }
        return "نامشخص"
    }
}