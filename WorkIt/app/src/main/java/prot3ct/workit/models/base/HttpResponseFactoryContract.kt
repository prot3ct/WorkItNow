package prot3ct.workit.models.base

interface HttpResponseFactoryContract {
    fun createResponse(
        headers: Map<String, List<String>>, body: String,
        message: String, code: Int
    ): HttpResponseContract
}