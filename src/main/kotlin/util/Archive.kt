package util

import com.squareup.moshi.Moshi
import data.Global
import parser.JsonParser
import java.io.File

object Archive {
    fun loadKeywordsFromFile(file: File):Boolean {
        //TODO: add exception process
        val adapter = Moshi.Builder().build().adapter<MutableMap<String,Object>>(MutableMap::class.java)
        val jsonData = file.bufferedReader().use { it.readText() }
        val jsonStructure= adapter.fromJson(jsonData)
        for ((key, value) in jsonStructure!!) {
            Global.replyDictionary[key.toLong()] = value as MutableMap<String, String>
        }
        return true
    }

    fun saveKeywordsToFile(file: File):Boolean {
        val adapter = Moshi.Builder().build().adapter(Any::class.java)
        return try {
            val jsonContent = adapter.toJson(Global.replyDictionary)
            file.bufferedWriter().use{it.write(jsonContent)}
            true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            false
        }
    }
}