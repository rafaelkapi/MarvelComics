package com.cactus.marvelcomics.data.network

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

interface HashGenerate {
    fun generate(timestamp: Long, privateKey: String, publicKey: String): String
}

/*****
 *
 *    credits:  https://github.com/rviannaoliveira/MarvelApp/blob/master/app/src/main/java/com/rviannaoliveira/marvelapp/data/api/MarvelHashGenerate.kt
 *
 *    thanks to Rodrigo Vianna
 */
class HashGenerateImp @Inject constructor() : HashGenerate {

    override fun generate(timestamp: Long, privateKey: String, publicKey: String): String {
        try {
            val concatResult = timestamp.toString() + privateKey + publicKey
            return md5(concatResult)
        } catch (e: Exception) {
            return ""
        }
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun md5(s: String): String {
        val digest = MessageDigest.getInstance("MD5")
        digest.update(s.toByteArray())
        val messageDigest = digest.digest()
        val bigInt = BigInteger(1, messageDigest)
        var hashText = bigInt.toString(16)
        while (hashText.length < 32) {
            hashText = "0" + hashText
        }
        return hashText
    }
}
