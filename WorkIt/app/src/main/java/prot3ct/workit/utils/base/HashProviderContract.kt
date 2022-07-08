package prot3ct.workit.utils.base

interface HashProviderContract {
    fun hashPassword(password: String): String
}