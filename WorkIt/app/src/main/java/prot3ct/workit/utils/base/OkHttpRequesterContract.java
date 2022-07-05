package prot3ct.workit.utils.base;

import java.util.Map;

import io.reactivex.Observable;
import prot3ct.workit.models.base.HttpResponseContract;

public interface OkHttpRequesterContract {

    Observable<HttpResponseContract> get(final String url);

    Observable<HttpResponseContract> get(final String url, final Map<String, String> headers);

    Observable<HttpResponseContract> post(final String url, final Map<String, String> body);

    Observable<HttpResponseContract> post(final String url, final Map<String, String> body, final Map<String, String> headers);
}