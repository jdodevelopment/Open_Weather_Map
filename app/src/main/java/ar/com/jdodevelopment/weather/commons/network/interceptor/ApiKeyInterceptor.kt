package ar.com.jdodevelopment.weather.commons.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response


class ApiKeyInterceptor : Interceptor {


    companion object  {

        const val API_KEY = "b9416258dfb764ca32aaf00d9b0ef419"
        const val QUERY_PARAM_NAME = "appid"

    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val httpUrl = request.url.newBuilder().addQueryParameter(QUERY_PARAM_NAME, API_KEY).build()
        request = request.newBuilder().url(httpUrl).build()
        return chain.proceed(request)
    }

}