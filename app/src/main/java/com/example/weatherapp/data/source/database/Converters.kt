package com.example.weatherapp.data.source.database

import androidx.room.TypeConverter
import java.util.*

object Converters {

    @TypeConverter
    @JvmStatic
    fun dateToLong(date: Date?): Long {
        return date?.time ?: 0
    }

    @TypeConverter
    @JvmStatic
    fun longToDate(long: Long): Date {
        return Date(long)
    }
}