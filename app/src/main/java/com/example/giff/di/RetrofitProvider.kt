package com.example.giff.di

import com.example.giff.GiphyApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private const val BASE_URL = "https://api.giphy.com/v1/"
    private const val API_KEY = "hGHA6dgGI9A8CVZUkV4Y3XVjef8EShhs"

    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .build()

    fun provideRetrofit() =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttp())
            .build()

    fun provideGiphyApi(): GiphyApi = provideRetrofit().create(GiphyApi::class.java)

    // Перехватывает запрос и подставляет api-key (Чтобы в каждом запросе не писать)
    class ApiKeyInterceptor: Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY).build()
            request.url(url)
            println("REQUEST GIF "+ url)
            return chain.proceed(request.build())
        }
    }

}