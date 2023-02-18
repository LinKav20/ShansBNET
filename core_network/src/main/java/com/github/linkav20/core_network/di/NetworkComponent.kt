package com.github.linkav20.core_network.di

import com.github.linkav20.core_network.BuildConfig
import com.github.linkav20.core_network.api.MyApi
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface NetworkComponent {
    fun api(): MyApi
}

@Module
abstract class NetworkModule {
    companion object{
        private const val base_url = "http://shans.d2.i-partner.ru/"

        @Provides
        @Singleton
        fun provideApi(): MyApi =
            Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level =
                                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                                else HttpLoggingInterceptor.Level.NONE
                        })
                        .build()
                )
                .build()
                .create(MyApi::class.java)
    }
}
