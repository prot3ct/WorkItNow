package prot3ct.workit.utils.base

import io.reactivex.Observable
import prot3ct.workit.models.base.HttpResponseContract

interface OkHttpRequesterContract {
    operator fun get(url: String): Observable<HttpResponseContract>
    operator fun get(
        url: String,
        headers: Map<String, String>
    ): Observable<HttpResponseContract>

    fun post(url: String, body: Map<String, String>): Observable<HttpResponseContract>
    fun post(
        url: String,
        body: Map<String, String>,
        headers: Map<String, String>
    ): Observable<HttpResponseContract>
}