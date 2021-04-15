package ar.com.jdodevelopment.weather.data.service


import ar.com.jdodevelopment.weather.data.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.*


interface WeatherService {


    @GET("weather")
    suspend fun findByCityId(@Query("id") cityId: Int): Response<WeatherResponse>

}