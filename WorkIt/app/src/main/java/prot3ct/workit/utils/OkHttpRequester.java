package prot3ct.workit.utils;

import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import prot3ct.workit.models.HttpResponseFactory;
import prot3ct.workit.models.base.HttpResponseContract;
import prot3ct.workit.models.base.HttpResponseFactoryContract;
import prot3ct.workit.utils.base.OkHttpRequesterContract;

public class OkHttpRequester implements OkHttpRequesterContract {

    private final HttpResponseFactoryContract responseFactory;
    private final OkHttpClient httpClient;

    public OkHttpRequester() {
        this.responseFactory = new HttpResponseFactory();
        this.httpClient = new OkHttpClient();
    }

    public Observable<HttpResponseContract> get(final String url) {
        return Observable.defer(new Callable<ObservableSource<? extends HttpResponseContract>>() {
            @Override
            public ObservableSource<? extends HttpResponseContract> call() throws Exception {
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                return createResponse(request);
            }
        });
    }

    public Observable<HttpResponseContract> get(final String url, final String searchQuery) {
        return Observable.defer(new Callable<ObservableSource<? extends HttpResponseContract>>() {
            @Override
            public ObservableSource<? extends HttpResponseContract> call() throws Exception {
                HttpUrl newUrl = HttpUrl.parse(url);
                newUrl = newUrl.newBuilder().setQueryParameter("search", searchQuery).build();

                Request request = new Request.Builder()
                        .url(newUrl)
                        .build();

                return createResponse(request);
            }
        });
    }

    public Observable<HttpResponseContract> get(final String url, final String searchQuery, final Map<String, String> headers) {
        return Observable.defer(new Callable<ObservableSource<? extends HttpResponseContract>>() {
            @Override
            public ObservableSource<? extends HttpResponseContract> call() throws Exception {
                HttpUrl newUrl = HttpUrl.parse(url);
                newUrl = newUrl.newBuilder().setQueryParameter("search", searchQuery).build();

                Request.Builder requestBuilder = new Request.Builder()
                        .url(newUrl);

                for (Map.Entry<String, String> pair : headers.entrySet()) {
                    requestBuilder.addHeader(pair.getKey(), pair.getValue());
                }

                Request request = requestBuilder.build();
                return createResponse(request);
            }
        });
    }


    public Observable<HttpResponseContract> get(final String url, final Map<String, String> headers) {
        return Observable.defer(new Callable<ObservableSource<? extends HttpResponseContract>>() {
            @Override
            public ObservableSource<? extends HttpResponseContract> call() throws Exception {
                Request.Builder requestBuilder = new Request.Builder()
                        .url(url);

                for (Map.Entry<String, String> pair : headers.entrySet()) {
                    requestBuilder.addHeader(pair.getKey(), pair.getValue());
                }

                Request request = requestBuilder.build();
                return createResponse(request);
            }
        });
    }

    public Observable<HttpResponseContract> post(final String url, final Map<String, String> body) {
        return Observable.defer(new Callable<ObservableSource<? extends HttpResponseContract>>() {
            @Override
            public ObservableSource<? extends HttpResponseContract> call() throws Exception {
                RequestBody requestBody = createRequestBody(body);

                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();

                return createResponse(request);
            }
        });
    }

    public Observable<HttpResponseContract> post(final String url, final Map<String, String> body,
                                                 final Map<String, String> headers) {

        return Observable.defer(new Callable<ObservableSource<? extends HttpResponseContract>>() {
            @Override
            public ObservableSource<? extends HttpResponseContract> call() throws Exception {
                RequestBody requestBody = createRequestBody(body);

                Request.Builder requestBuilder = new Request.Builder()
                        .url(url)
                        .post(requestBody);

                for (Map.Entry<String, String> pair : headers.entrySet()) {
                    requestBuilder.addHeader(pair.getKey(), pair.getValue());
                }

                Request request = requestBuilder.build();
                return createResponse(request);
            }
        });
    }

    public Observable<HttpResponseContract> put(final String url, final Map<String, String> body) {
        return Observable.defer(new Callable<ObservableSource<? extends HttpResponseContract>>() {
            @Override
            public ObservableSource<? extends HttpResponseContract> call() throws Exception {
                RequestBody requestBody = createRequestBody(body);

                Request request = new Request.Builder()
                        .url(url)
                        .put(requestBody)
                        .build();

                return createResponse(request);
            }
        });
    }

    public Observable<HttpResponseContract> put(final String url, final Map<String, String> body, final Map<String, String> headers) {
        return Observable.defer(new Callable<ObservableSource<? extends HttpResponseContract>>() {
            @Override
            public ObservableSource<? extends HttpResponseContract> call() throws Exception {
                RequestBody requestBody = createRequestBody(body);

                Request.Builder requestBuilder = new Request.Builder()
                        .url(url)
                        .put(requestBody);

                for (Map.Entry<String, String> pair : headers.entrySet()) {
                    requestBuilder.addHeader(pair.getKey(), pair.getValue());
                }

                Request request = requestBuilder.build();
                return createResponse(request);
            }
        });
    }

    public Observable<HttpResponseContract> delete(final String url) {
        return Observable.defer(new Callable<ObservableSource<? extends HttpResponseContract>>() {
            @Override
            public ObservableSource<? extends HttpResponseContract> call() throws Exception {
                Request request = new Request.Builder()
                        .url(url)
                        .delete()
                        .build();

                return createResponse(request);
            }
        });
    }

    public Observable<HttpResponseContract> delete(final String url, final Map<String, String> headers) {
        return Observable.defer(new Callable<ObservableSource<? extends HttpResponseContract>>() {
            @Override
            public ObservableSource<? extends HttpResponseContract> call() throws Exception {
                Request.Builder requestBuilder = new Request.Builder()
                        .url(url)
                        .delete();


                for (Map.Entry<String, String> pair : headers.entrySet()) {
                    requestBuilder.addHeader(pair.getKey(), pair.getValue());
                }

                Request request = requestBuilder.build();
                return createResponse(request);
            }
        });
    }

    private RequestBody createRequestBody(Map<String, String> bodyMap) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();

        for (Map.Entry<String, String> pair : bodyMap.entrySet()) {
            bodyBuilder.add(pair.getKey(), pair.getValue());
        }


        return bodyBuilder.build();
    }

    private Observable<HttpResponseContract> createResponse(Request request) {
        try {
            Response response = httpClient.newCall(request).execute();

            HttpResponseContract responseParsed = responseFactory.createResponse(
                    response.headers().toMultimap(), response.body().string(),
                    response.message(), response.code());
            return Observable.just(responseParsed);
        } catch (IOException e) {
            return Observable.error(e);
        }
    }
}