package com.example.nome.ui.network

import android.content.Context
import com.example.nome.model.Preset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import java.lang.reflect.Type

interface iFetchPresets{
    suspend fun fetchPresets(): List<Preset>

}
//build client
//build request
//build client new call and then call execute
class PresetsFetcher(private val ctx: Context) : iFetchPresets {
    private val URL = "https://my-json-server.typicode.com/shangnang/TowsonNvilla4/online_database"
    private val _client: OkHttpClient = OkHttpClient()

    override suspend fun fetchPresets(): List<Preset> {
        return withContext(Dispatchers.IO){
            val request: Request = Request.Builder()
                .url(URL)
                .get()
                .build()
            val response: Response = _client.newCall(request).execute()
            if(response.isSuccessful){
                val responseBody: ResponseBody? = response.body
                val json = responseBody?.string()
                if(json != null){
                    val gson = Gson()
                    val listItemType = object: TypeToken<List<Preset>>() {}.type
                    val presetList: List<Preset> = gson.fromJson(json, listItemType)
                    presetList
                } else {
                    throw Exception("Json list is null")
                }
            } else{
                throw Exception("Error fetching")
            }
        }

    }

}


