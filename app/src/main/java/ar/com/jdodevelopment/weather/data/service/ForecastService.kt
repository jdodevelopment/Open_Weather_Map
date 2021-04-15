package ar.com.jdodevelopment.weather.data.service


import ar.com.jdodevelopment.weather.data.response.ForecastResponse
import retrofit2.Response
import retrofit2.http.*


interface ForecastService {


    @GET("forecast")
    suspend fun findByCityId(@Query("id") cityId: Int): Response<ForecastResponse>

}