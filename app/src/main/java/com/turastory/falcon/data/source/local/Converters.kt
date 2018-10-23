package com.turastory.falcon.data.source.local

import android.arch.persistence.room.TypeConverter
import java.util.*


/**
 * Created by tura on 2018-10-22.
 */

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(value) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}