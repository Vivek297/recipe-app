package com.example.food1.di

import com.example.food1.data.network.FoodRecipesApi
import com.example.food1.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module // Modules are used to declare how to provide instances of certain types.
@InstallIn(SingletonComponent::class) // This means that the provided instances will have a singleton scope.
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpClient():OkHttpClient{
        return OkHttpClient.Builder()
            .readTimeout(15,java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(15,java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }
    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,gsonConverterFactory: GsonConverterFactory
    ):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): FoodRecipesApi {
        return retrofit.create(FoodRecipesApi::class.java)
    }
}