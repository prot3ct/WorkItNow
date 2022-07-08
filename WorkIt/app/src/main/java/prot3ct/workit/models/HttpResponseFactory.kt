package prot3ct.workit.models

import prot3ct.workit.models.base.HttpResponseFactoryContract
import prot3ct.workit.models.base.HttpResponseContract

class HttpResponseFactory : HttpResponseFactoryContract {
    override fun createResponse(
        headers: Map<String, List<String>>, body: String,
        message: String, code: Int
    ): HttpResponseContract {
        return object : HttpResponseContract {
            override fun getHeaders(): Map<String, List<String>> {
                return headers
            }

            override fun getBody(): String {
                return body
            }

            override fun getMessage(): String {
                return message
            }

            override fun getCode(): Int {
                return code
            }
        }
    }
}