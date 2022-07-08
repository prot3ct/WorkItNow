package prot3ct.workit.models.base

interface HttpResponseContract {
    val headers: Map<String?, List<String?>?>?
    val body: String?
    val message: String?
    val code: Int
}