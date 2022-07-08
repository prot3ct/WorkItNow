package prot3ct.workit.models.base

import prot3ct.workit.models.base.HttpResponseContract

interface HttpResponseFactoryContract {
    fun createResponse(
        headers: Map<String?, List<String?>?>?, body: String?,
        message: String?, code: Int
    ): HttpResponseContract?
}