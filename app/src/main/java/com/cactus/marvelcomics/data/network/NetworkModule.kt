package com.cactus.marvelcomics.data.network

import android.app.Application
import com.cactus.marvelcomics.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [InnerNetworkModule::class])
class NetworkModule {

    @Singleton
    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = NetworkConstants.baseUrl

    @Singleton
    @Provides
    @SubscriptionKey
    fun provideSubscriptionKey(): String = NetworkConstants.publicKey

    @Singleton
    @Provides
    @PrivateKey
    fun providePrivateKey(): String = NetworkConstants.privateKey

    @Singleton
    @Provides
    @LoggingInterceptor
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun providesOkHttpClient(
        @LoggingInterceptor loggingInterceptor: HttpLoggingInterceptor,
        subscriptionKeyInterceptor: SubscriptionKeyInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(subscriptionKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()


    @Singleton
    @Provides
    fun retrofit(
        okhttpClient: OkHttpClient,
        moshi: Moshi,
        @BaseUrl baseUrl: String
    ): Retrofit =
        Retrofit.Builder()
            .client(okhttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()


    private val nullOnEmptyConverterFactory = object : Converter.Factory() {
        fun converterFactory() = this
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ) = object :
            Converter<ResponseBody, Any?> {
            val nextResponseBodyConverter =
                retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)

            override fun convert(value: ResponseBody) =
                if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
        }
    }
}

@Module
abstract class InnerNetworkModule {
    @Singleton
    @Binds
    abstract fun providesHashGenerattor(hashGenerate: HashGenerateImp): HashGenerate
}
