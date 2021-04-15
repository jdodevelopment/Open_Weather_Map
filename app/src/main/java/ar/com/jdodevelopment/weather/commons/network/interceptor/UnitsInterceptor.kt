package ar.com.jdodevelopment.weather.commons.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response


class UnitsInterceptor constructor(

) : Interceptor {


    companion object  {

        const val UNIT_CODE = "metric"
        const val QUERY_PARAM_NAME = "units"

    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val httpUrl = request.url.newBuilder().addQueryParameter(QUERY_PARAM_NAME, UNIT_CODE).build()
        request = request.newBuilder().url(httpUrl).build()
        return chain.proceed(request)
    }

}