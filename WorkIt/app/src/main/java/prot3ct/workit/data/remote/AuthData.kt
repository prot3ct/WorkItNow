package prot3ct.workit.data.remote

import android.content.Context
import io.reactivex.Observable
import prot3ct.workit.data.remote.base.AuthDataContract
import prot3ct.workit.utils.OkHttpRequester
import prot3ct.workit.config.ApiConstants
import prot3ct.workit.utils.GsonParser
import prot3ct.workit.data.local.UserSession
import prot3ct.workit.view_models.LoginViewModel
import java.lang.Error
import java.util.HashMap

class AuthData(private val context: Context) : AuthDataContract {
    private val httpRequester: OkHttpRequester = OkHttpRequester()

    private val hashProvider: HashProvider = HashProvider()

    private val apiConstants: ApiConstants = ApiConstants()

    private val gsonParser: GsonParser = GsonParser()

    private val userSession: UserSession = UserSession(context)

    override fun login(email: String, password: String): Observable<Boolean> {
        val userCredentials: MutableMap<String, String> = HashMap()
        val passHash = hashProvider.hashPassword(password)
        userCredentials["email"] = email
        userCredentials["passHash"] = passHash
        return httpRequester
            .post(apiConstants.loginUrl(), userCredentials)
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                val responseBody = iHttpResponse.body
                val result =
                    gsonParser.fromJson<LoginViewModel>(responseBody, LoginViewModel::class.java)
                userSession.id = result.userId
                userSession.email = result.email
                userSession.fullName = result.fullName
                userSession.accessToken = result.accessToken
                true
            }
    }

    override fun register(email: String, fullName: String, password: String): Observable<Boolean> {
        val userCredentials: MutableMap<String, String> = HashMap()
        val passHash = hashProvider.hashPassword(password)
        userCredentials["email"] = email
        userCredentials["fullName"] = fullName
        userCredentials["passHash"] = passHash
        return httpRequester
            .post(apiConstants.registerUrl(), userCredentials)
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                true
            }
    }

    override fun autoLogin(): Observable<Boolean> {
        val body: MutableMap<String, String?> = HashMap()
        body["userId"] = userSession.id.toString() + ""
        body["authToken"] = userSession.accessToken
        return httpRequester
            .post(apiConstants.autoLoginUserUrl(), body)
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                java.lang.Boolean.parseBoolean(iHttpResponse.body)
            }
    }

    override val loggedInUserId: Int
        get() = userSession.loggedInUserId
    override val loggedInUserName: String
        get() = userSession.fullName!!

    override fun logoutUser() {
        userSession.clearSession()
    }

    override val isLoggedIn: Boolean
        get() = userSession.isLoggedIn

}