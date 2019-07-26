package util

import com.squareup.moshi.Moshi
import data.Global
import java.io.File

object Archive {
    fun loadKeywordsFromFile(file: File):Boolean {
        //TODO: add exception process
        val adapter = Moshi.Builder().build().adapter<MutableMap<String,Any>>(MutableMap::class.java)
        val jsonData = file.bufferedReader().use { it.readText() }
        val jsonStructure= adapter.fromJson(jsonData)
        for ((key, value) in jsonStructure!!) {
            Global.replyDictionary[key.toLong()] = value as MutableMap<String, String>
        }
        return true
    }

    fun saveKeywordsToFile(file: File):Boolean {
        val adapter = Moshi.Builder().build().adapter<MutableMap<Long,MutableMap<String,String>>>(MutableMap::class.java)
        return try {
            val jsonContent = adapter.toJson(Global.replyDictionary)
            if (!file.exists()) {

            }
            file.writeText(jsonContent)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}