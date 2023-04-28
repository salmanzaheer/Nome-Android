package com.example.nome.ui.network

import com.example.nome.model.Preset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.lang.reflect.Type

interface iFetchPresets{
    suspend fun fetchPresets(): List<Preset>

}
//build client
//build request
//build client new call and then call execute
class PresetsFetcher : iFetchPresets{
    private val URL = "https://api.jsonbin.io/v3/b/644b6ce69d312622a353a788"
    private val _client: OkHttpClient = OkHttpClient()


    override suspend fun fetchPresets(): List<Preset> {
        val request: Request = Request.Builder()
            .url(URL)
            .get()
            .build()
        val response: Response =_client.newCall(request).execute()
        if(response.isSuccessful){
            //parse
            val responseBody: ResponseBody? = response.body
            val json: String? = responseBody?.string()
            if(json != null){
                val gson = Gson()
                val listItemType = object: TypeToken<List<String>>() {}.type
                val songList: List<Preset> = gson.fromJson(json, listItemType)
                return songList
            } else {
                throw Exception("Json is null")
            }
        }
        else {
            throw Exception("Error fetching song list")
        }
    }


}