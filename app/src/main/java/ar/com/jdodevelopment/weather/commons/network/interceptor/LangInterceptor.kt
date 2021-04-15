package ar.com.jdodevelopment.weather.commons.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response


class LangInterceptor : Interceptor {


    companion object  {

        const val LENGUAGE_CODE = "es"
        const val QUERY_PARAM_NAME = "lang"

    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val httpUrl = request.url.newBuilder().addQueryParameter(QUERY_PARAM_NAME, LENGUAGE_CODE).build()
        request = request.newBuilder().url(httpUrl).build()
        return chain.proceed(request)
    }

}