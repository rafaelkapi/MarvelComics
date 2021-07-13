package com.cactus.marvelcomics.data.network

import  br.com.marvel.network.PrivateKey
import br.com.marvel.network.SubscriptionKey
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class SubscriptionKeyInterceptor @Inject constructor(
    @SubscriptionKey private val subscriptionKey: String,
    @PrivateKey private val privateKey: String,
    private val hashGenerate: HashGenerate
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        var request = chain.request()
        val builder = request.url.newBuilder()

        builder.addQueryParameter(API_PARAMETER, subscriptionKey)
            .addQueryParameter(HASH_PARAMETER, hashGenerate.generate(timeStamp, privateKey, subscriptionKey))
            .addQueryParameter(TS_PARAMETER, timeStamp.toString())

        request = request.newBuilder().url(builder.build()).build()
        return chain.proceed(request)
    }

    companion object {
        const val API_PARAMETER = "apikey"
        const val HASH_PARAMETER = "hash"
        const val TS_PARAMETER = "ts"
    }
}
