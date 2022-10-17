package com.example.myapplication.data.api

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private var instance: Retrofit? = null
    private var recipesApi: RecipesApi? = null
    var baseUrl = "https://frozen-temple-18740.herokuapp.com/"

    fun getInstance(context: Context): Retrofit {
        if (instance == null) {
            val cacheSize = (10 * 1024 * 1024).toLong()  //10Mb
            val client = OkHttpClient.Builder()
                .cache(Cache(context.cacheDir, cacheSize))
                .build()

            instance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return instance!!
    }

    fun getRecipesApi(context: Context): RecipesApi {
        if(recipesApi == null) {
            recipesApi = getInstance(context).create(RecipesApi::class.java)
        }
        return recipesApi!!
    }


}