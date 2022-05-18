package dev.ogabek.testapp.networking

import dev.ogabek.testapp.networking.service.ProductService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHttp {

    private val IS_TESTER = true
    private val SERVER_DEVELOPMENT = "https://valixon.bexatobot.uz/" // We have not DEVELOPMENT SERVER YET
    private val SERVER_PRODUCTION = "https://valixon.bexatobot.uz/"

    val retrofit = Retrofit.Builder().baseUrl(server()).addConverterFactory(GsonConverterFactory.create()).build()

    private fun server(): String {
        if (IS_TESTER) return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    val productService: ProductService = retrofit.create(ProductService::class.java)

}