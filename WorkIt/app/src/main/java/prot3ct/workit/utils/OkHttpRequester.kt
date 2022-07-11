package prot3ct.workit.utils

import io.reactivex.Observable
import prot3ct.workit.utils.base.OkHttpRequesterContract
import prot3ct.workit.models.base.HttpResponseFactoryContract
import prot3ct.workit.models.base.HttpResponseContract
import okhttp3.*
import prot3ct.workit.models.HttpResponseFactory
import java.io.IOException

class OkHttpRequester : OkHttpRequesterContract {
    private val responseFactory: HttpResponseFactoryContract
    private val httpClient: OkHttpClient
    override fun get(url: String): Observable<HttpResponseContract> {
        return Observable.defer {
            val request = Request.Builder()
                .url(url)
                .build()
            createResponse(request)
        }
    }

    operator fun get(url: String?, searchQuery: String?): Observable<HttpResponseContract> {
        return Observable.defer {
            var newUrl = HttpUrl.parse(url)
            newUrl = newUrl!!.newBuilder().setQueryParameter("search", searchQuery).build()
            val request = Request.Builder()
                .url(newUrl)
                .build()
            createResponse(request)
        }
    }

    operator fun get(
        url: String?,
        searchQuery: String?,
        headers: Map<String?, String?>
    ): Observable<HttpResponseContract> {
        return Observable.defer {
            var newUrl = HttpUrl.parse(url)
            newUrl = newUrl!!.newBuilder().setQueryParameter("search", searchQuery).build()
            val requestBuilder = Request.Builder()
                .url(newUrl)
            for ((key, value) in headers) {
                requestBuilder.addHeader(key, value)
            }
            val request = requestBuilder.build()
            createResponse(request)
        }
    }

    override fun get(url: String, headers: Map<String, String>): Observable<HttpResponseContract> {
        return Observable.defer {
            val requestBuilder = Request.Builder()
                .url(url)
            for ((key, value) in headers) {
                requestBuilder.addHeader(key, value)
            }
            val request = requestBuilder.build()
            createResponse(request)
        }
    }

    override fun post(url: String, body: Map<String, String>): Observable<HttpResponseContract> {
        return Observable.defer {
            val requestBody = createRequestBody(body)
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
            createResponse(request)
        }
    }

    override fun post(
        url: String, body: Map<String, String>,
        headers: Map<String, String>
    ): Observable<HttpResponseContract> {
        return Observable.defer {
            val requestBody = createRequestBody(body)
            val requestBuilder = Request.Builder()
                .url(url)
                .post(requestBody)
            for ((key, value) in headers) {
                requestBuilder.addHeader(key, value)
            }
            val request = requestBuilder.build()
            createResponse(request)
        }
    }

    fun put(url: String?, body: Map<String, String>): Observable<HttpResponseContract> {
        return Observable.defer {
            val requestBody = createRequestBody(body)
            val request = Request.Builder()
                .url(url)
                .put(requestBody)
                .build()
            createResponse(request)
        }
    }

    fun put(
        url: String?,
        body: Map<String, String?>,
        headers: Map<String?, String?>
    ): Observable<HttpResponseContract> {
        return Observable.defer {
            val requestBody = createRequestBody(body)
            val requestBuilder = Request.Builder()
                .url(url)
                .put(requestBody)
            for ((key, value) in headers) {
                requestBuilder.addHeader(key, value)
            }
            val request = requestBuilder.build()
            createResponse(request)
        }
    }

    fun delete(url: String?): Observable<HttpResponseContract> {
        return Observable.defer {
            val request = Request.Builder()
                .url(url)
                .delete()
                .build()
            createResponse(request)
        }
    }

    fun delete(url: String?, headers: Map<String?, String?>): Observable<HttpResponseContract> {
        return Observable.defer {
            val requestBuilder = Request.Builder()
                .url(url)
                .delete()
            for ((key, value) in headers) {
                requestBuilder.addHeader(key, value)
            }
            val request = requestBuilder.build()
            createResponse(request)
        }
    }

    private fun createRequestBody(bodyMap: Map<String, String?>): RequestBody {
        val bodyBuilder = FormBody.Builder()
        for ((key, value) in bodyMap) {
            value?.let {
                bodyBuilder.add(key, value)
            }
        }
        return bodyBuilder.build()
    }

    private fun createResponse(request: Request): Observable<HttpResponseContract?> {
        return try {
            val response = httpClient.newCall(request).execute()
            val responseParsed = responseFactory.createResponse(
                response.headers().toMultimap(), response.body()!!.string(),
                response.message(), response.code()
            )
            Observable.just(responseParsed)
        } catch (e: IOException) {
            Observable.error(e)
        }
    }

    init {
        responseFactory = HttpResponseFactory()
        httpClient = OkHttpClient()
    }
}