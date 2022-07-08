package prot3ct.workit.models

import prot3ct.workit.models.base.HttpResponseFactoryContract
import prot3ct.workit.models.base.HttpResponseContract

class HttpResponseFactory : HttpResponseFactoryContract {
    override fun createResponse(
        headers: Map<String, List<String>>, body: String,
        message: String, code: Int
    ): HttpResponseContract {
        return object : HttpResponseContract {
            override val headers: Map<String, List<String>>
                get() = headers
            override val body: String
                get() = body
            override val message: String
                get() = message
            override val code: Int
                get() = code
        }
    }
}