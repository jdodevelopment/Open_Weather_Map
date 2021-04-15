package ar.com.jdodevelopment.weather.di


import ar.com.jdodevelopment.weather.commons.network.interceptor.ApiKeyInterceptor
import ar.com.jdodevelopment.weather.commons.network.interceptor.LangInterceptor
import ar.com.jdodevelopment.weather.commons.network.interceptor.UnitsInterceptor
import ar.com.jdodevelopment.weather.data.service.ForecastService
import ar.com.jdodevelopment.weather.data.service.WeatherService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class OpenWeatherMapApiModule {


    companion object {

        private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
        private const val TIMEOUT_SECONDS = 25L

    }


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: ApiKeyInterceptor,
        langInterceptor: LangInterceptor,
        unitsInterceptor: UnitsInterceptor,
        ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(langInterceptor)
            .addInterceptor(unitsInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): ApiKeyInterceptor {
        return ApiKeyInterceptor()
    }

    @Provides
    @Singleton
    fun provideLangInterceptor(): LangInterceptor {
        return LangInterceptor()
    }

    @Provides
    @Singleton
    fun provideUnitsInterceptor(): UnitsInterceptor {
        return UnitsInterceptor()
    }

    @Provides
    fun provideWeatherService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    @Provides
    fun provideForecastService(retrofit: Retrofit): ForecastService {
        return retrofit.create(ForecastService::class.java)
    }


}
