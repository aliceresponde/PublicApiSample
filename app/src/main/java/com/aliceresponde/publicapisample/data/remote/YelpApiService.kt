package com.aliceresponde.publicapisample.data.remote

import com.aliceresponde.publicapisample.BuildConfig
import com.aliceresponde.publicapisample.BuildConfig.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface YelpApiService {
    companion object {
        private val PRIVATE_API_KEY_ARG = "Authorization"

        operator fun invoke(interceptor: Interceptor): YelpApiService {
            val logInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor {chain ->
                    val defaultRequest = chain.request()
                    val headers = defaultRequest.headers.newBuilder()
                        .add(
                            PRIVATE_API_KEY_ARG,
                            BuildConfig.API_KEY_VALUE
                        ).build()
                    val requestBuilder = defaultRequest.newBuilder().headers(headers)
                    chain.proceed(requestBuilder.build())
                }
                .addInterceptor(interceptor)
                .addInterceptor(logInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(YelpApiService::class.java)
        }
    }

    @GET("v3/businesses/search")
    suspend fun getBusinessByLocation(@Query("location") location: String): Response<BusinessResponse>

    @GET("v3/businesses/{id}")
    suspend fun getBusinessDetailsById(@Path("id") businessId: String): Response<BusinessDetailResponse>
}