package com.example.weatherapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import java.text.SimpleDateFormat
import java.util.*


object  Utils {


    const val DATE_FORMAT_EEEE_DD_MMM_YYYY ="EEEE, dd-MMM-yy"
    const val DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
    const val DATE_FORMAT_hh_mm_a ="hh:mm a"


    fun parseDateInSpecificFormat(date:String?, sourceFormat:String, targetFormat:String):String{
        val sourceDateFormat = SimpleDateFormat(sourceFormat,Locale.getDefault())
        val sourceDate = sourceDateFormat.parse(date)
        val simpleDateFormat = SimpleDateFormat(targetFormat,Locale.getDefault())
        return simpleDateFormat.format(sourceDate)

    }

    fun parseUnixTimeStampInSpecificFormat(unix:Int,targetFormat:String):String{
        val date = Date(unix*1000L)
        val simpleDateFormat = SimpleDateFormat(targetFormat,Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getDefault()
        return simpleDateFormat.format(date)

    }


  fun isTomorrow(date:String?,sourceFormat:String):Boolean{
      val sourceDateFormat = SimpleDateFormat(sourceFormat,Locale.getDefault())
      val sourceDate = sourceDateFormat.parse(date)
      val soureCalendar= Calendar.getInstance()
      soureCalendar.time =sourceDate
      val tomorrowDate = Calendar.getInstance()
      tomorrowDate.add(Calendar.DATE,1)
      return  (soureCalendar.get(Calendar.DATE)==tomorrowDate.get(Calendar.DATE))
  }

    fun isToday(date:String?,sourceFormat:String):Boolean{
        val sourceDateFormat = SimpleDateFormat(sourceFormat,Locale.getDefault())
        val sourceDate = sourceDateFormat.parse(date)
        val soureCalendar= Calendar.getInstance()
        soureCalendar.time =sourceDate
        val tomorrowDate = Calendar.getInstance()
        return  (soureCalendar.get(Calendar.DATE)==tomorrowDate.get(Calendar.DATE))
    }






}
fun String.showToast(context: Context?){
    Toast.makeText(context,this,Toast.LENGTH_SHORT).show()
}

fun View.hideKeyboard(){
    (this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.let {
        it.hideSoftInputFromWindow(this.windowToken, 0)
    }

}