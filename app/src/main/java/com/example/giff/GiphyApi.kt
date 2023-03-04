package com.example.giff

import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("gifs/search")
    suspend fun getGifs(
        @Query("q") query: String?,
    ): DataResult
}