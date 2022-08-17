package com.demoapps.newsapp.data.state

import java.util.*

class AppState(
    val searchQuery: String = "android",
    val searchIn: String = "title",
    val sortBy: String = "publishedAt",
    var searchFrom:String = "2022-08-01",
    ){
     init {
         val mCalendar = Calendar.getInstance()
         searchFrom = "${mCalendar.get(Calendar.YEAR)}-${mCalendar.get(Calendar.MONTH) + 1}-${mCalendar.get(Calendar.DAY_OF_MONTH) - 10}"
     }
 }
