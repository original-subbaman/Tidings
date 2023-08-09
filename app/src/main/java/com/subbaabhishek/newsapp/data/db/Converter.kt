package com.subbaabhishek.newsapp.data.db

import androidx.room.TypeConverter
import com.subbaabhishek.newsapp.data.model.Source

class Converter {

    @TypeConverter
    fun fromSource(source: Source) : String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String) : Source{
        return Source(name, name)
    }
}