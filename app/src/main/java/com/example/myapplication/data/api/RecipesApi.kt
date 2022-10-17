package com.example.myapplication.data.api

import com.example.myapplication.data.model.Recipe
import retrofit2.http.GET
import retrofit2.Call;
import retrofit2.http.Headers
import retrofit2.http.Path

interface RecipesApi {

    @Headers("Cache-Control: public, max-age=86400, max-stale=86400")
    @GET("/{locale}")
    fun getRecipes(@Path("locale") locale : String) : Call<List<Recipe>>

}