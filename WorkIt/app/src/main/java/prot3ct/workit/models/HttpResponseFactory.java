package prot3ct.workit.models;

import java.util.List;
import java.util.Map;

import prot3ct.workit.models.base.HttpResponseContract;
import prot3ct.workit.models.base.HttpResponseFactoryContract;

public class HttpResponseFactory implements HttpResponseFactoryContract {

    public HttpResponseContract createResponse(
            final Map<String, List<String>> headers, final String body,
            final String message, final int code) {

        return new HttpResponseContract() {
            @Override
            public Map<String, List<String>> getHeaders() {
                return headers;
            }

            @Override
            public String getBody() {
                return body;
            }

            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public int getCode() {
                return code;
            }
        };
    }
}