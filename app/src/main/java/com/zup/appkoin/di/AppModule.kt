package com.zup.appkoin.di

import com.zup.appkoin.BuildConfig
import com.zup.appkoin.api.request.MoviesApi
import com.zup.appkoin.view.adapter.MoviesRecycler
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }
    }

    single (named("INTERCEPTOR")) {
       object :Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url

                val url:HttpUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", "4b52fa3a04278ae92da164f3ee9820c5")
                    .addQueryParameter("language","pt-BR")
                    .build()
                val requestBuilder = original.newBuilder()
                    .url(url)

                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        }
    }

    single {
        OkHttpClient
            .Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<Interceptor>(named("INTERCEPTOR")))
            .build()
    }

    single {
        Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(get<OkHttpClient>())
                .build()
    }

    single {
        get<Retrofit>().create(MoviesApi::class.java)
    }


    factory {
        MoviesRecycler()
    }
}
