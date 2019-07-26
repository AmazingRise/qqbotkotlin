package parser

import com.squareup.moshi.Moshi
import java.io.File
import java.lang.Exception
import java.lang.reflect.Type

class JsonParser {
    fun <T> fromString(json: String, type: Class<T>):T? {
        val jsonAdapter = Moshi.Builder().build().adapter(type)
        return jsonAdapter.fromJson(json)
    }
    fun <T> toJson(objectToParse: T, type: Class<T>): String? {
        val jsonAdapter = Moshi.Builder().build().adapter(type)
        return jsonAdapter.toJson(objectToParse)
    }
}