package com.idat.proyectoevc4.servicio


import com.google.gson.GsonBuilder
import com.idat.proyectoevc4.modelo.Cita
import com.idat.proyectoevc4.modelo.Dueno
import com.idat.proyectoevc4.modelo.LoginRequest
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

object AppConstantes{
    const val base_url="http://192.168.1.7:8070"
}

interface WebService {

    @POST("/Cita/Ingresar")
    suspend fun ingresarCita(@Body cita: Cita): Response<Cita>

    @POST("Dueno/Ingresar")
    suspend fun ingresarDueno(@Body dueno: Dueno): Response<Dueno>

    @POST("/api/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<String>

}

object RetrofitCliente{

    val objWebService:WebService by lazy {
        Retrofit.Builder().
        baseUrl(AppConstantes.base_url)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}
