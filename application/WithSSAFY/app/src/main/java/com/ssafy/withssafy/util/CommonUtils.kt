package com.ssafy.withssafy.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.Date

object CommonUtils {
    fun convertEnglishMonth(month:Int):String{
        if(month == 1){
            return "January"
        }
        if(month == 2){
            return "February"
        }
        if(month == 3){
            return "March"
        }
        if(month == 4){
            return "April"
        }
        if(month == 5){
            return "May"
        }
        if(month == 6){
            return "June"
        }
        if(month == 7){
            return "July"
        }
        if(month == 8){
            return "August"
        }
        if(month == 9){
            return "September"
        }
        if(month == 10){
            return "October"
        }
        if(month == 11){
            return "November"
        }
        if(month == 12){
            return "December"
        }
        return ""
    }


    inline fun <reified T> parseDto(data: Any, typeToken: Type): T {
        val jsonResult: String = Gson().toJson(data)
        return Gson().fromJson<T>(jsonResult, typeToken)
    }
    
    fun convertWeek(week:Int):String{
        if(week == 0){
            return "일요일"
        }else if(week == 1){
            return "월요일"
        }else if(week == 2){
            return "화요일"
        }else if(week == 3){
            return "수요일"
        }else if(week == 4){
            return "목요일"
        }else if(week == 5){
            return "금요일"
        }else if(week == 6){
            return "토요일"
        }
        return ""
    }

    fun unixTimeToDateFormatInBoard(unixTime: Long): String {
        val simpleDateFormat = SimpleDateFormat("yy/MM/dd HH:mm") //"YYYY-MM-dd HH:mm:ss.SSS"
        val date = Date()
//        date.time = unixTime * 1000L
        date.time = unixTime

        return simpleDateFormat.format(date)
    }

    fun base64ToImg(img: String) : Bitmap {
        val imageBytes = Base64.decode(img, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    fun ellipsisContent(content: String) : String {
        return if(content.length > 11) {
            "${content.substring(0, 10)} •••"
        } else {
            content
        }
    }
}
