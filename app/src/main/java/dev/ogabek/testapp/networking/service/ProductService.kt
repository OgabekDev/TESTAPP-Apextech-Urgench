package dev.ogabek.testapp.networking.service

import dev.ogabek.testapp.model.Product
import dev.ogabek.testapp.model.login.LoginRespond
import dev.ogabek.testapp.model.login.LoginSend
import retrofit2.Call
import retrofit2.http.*

interface ProductService {

    @Headers("Content-Type: application/json")
    @POST("api/v1/authenticate")
    fun auth(@Body loginSend: LoginSend): Call<LoginRespond>

    @GET("api/v1/products")
    fun getProducts(@Header("Authorization") token: String): Call<ArrayList<Product>>

    @GET("api/v1/products/{id}")
    fun getProduct(@Header("Authorization") token: String, @Path("id") id: Int): Call<Product>

}