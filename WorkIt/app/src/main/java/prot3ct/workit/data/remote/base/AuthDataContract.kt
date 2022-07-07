package prot3ct.workit.data.remote.base

import io.reactivex.Observable

interface AuthDataContract {
    fun login(username: String, password: String): Observable<Boolean>

    fun register(email: String, fullName: String, password: String): Observable<Boolean>

    fun autoLogin(): Observable<Boolean>

    val loggedInUserId: Int

    val loggedInUserName: String

    fun logoutUser()

    val isLoggedIn: Boolean
}