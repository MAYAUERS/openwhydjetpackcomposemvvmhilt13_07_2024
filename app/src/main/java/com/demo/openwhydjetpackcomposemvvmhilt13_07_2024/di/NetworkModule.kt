package com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.di

import com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGsonConverterFactory() = GsonConverterFactory.create()


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor
            .setLevel(HttpLoggingInterceptor.Level.BODY)).build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,gsonConverterFactory: GsonConverterFactory):Retrofit =
        Retrofit.Builder()
            .baseUrl("https://openwhyd.org/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):ApiService
     = retrofit.create(ApiService::class.java)

}