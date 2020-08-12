package com.example.weatherapp.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.math.BigDecimal
import java.util.*

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("date")
fun setTextToHoursAndMinutes(textView: TextView, date: Date) {
    val calendar = GregorianCalendar.getInstance()
    calendar.setTime(date)
    val hoursString = calendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
    val minutesString = calendar.get(Calendar.MINUTE).toString().padStart(2, '0')
    val stringBuilder = StringBuilder()
    stringBuilder.append(hoursString).append(":").append(minutesString)
    textView.text = stringBuilder.toString()
}

@BindingAdapter("temperature")
fun setTextToTemperature(textView: TextView, temp: Float) {
    val tempCelsius = temp - 273f
    val bigDecimalTemp = round(tempCelsius, 2)
    val stringBuilder = StringBuilder()
    stringBuilder.append(bigDecimalTemp).append("°C")
    textView.text = stringBuilder.toString()
}

@BindingAdapter("humidity")
fun setTextToHumidity(textView: TextView, humidity: Int) {
    val humidityString = humidity.toString()
    val stringBuilder = StringBuilder()
    stringBuilder.append(humidityString).append("%")
    textView.text = stringBuilder.toString()
}

@BindingAdapter("wind_speed")
fun setTextToWindSpeed(textView: TextView, windSpeed: Float) {
    val windSpeedString = windSpeed.toString()
    val stringBuilder = StringBuilder()
    stringBuilder.append(windSpeedString).append(" m/s")
    textView.text = stringBuilder.toString()
}

fun round(d: Float, decimalPlace: Int): BigDecimal? {
    var bd = BigDecimal(java.lang.Float.toString(d))
    bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
    return bd
}