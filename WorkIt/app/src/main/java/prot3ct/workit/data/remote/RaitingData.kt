package prot3ct.workit.data.remote

import android.content.Context
import io.reactivex.Observable
import prot3ct.workit.data.remote.base.RaitingDataContract
import prot3ct.workit.utils.OkHttpRequester
import prot3ct.workit.config.ApiConstants
import prot3ct.workit.utils.GsonParser
import prot3ct.workit.data.local.UserSession
import java.lang.Error
import java.util.HashMap

class RaitingData(context: Context) : RaitingDataContract {
    private val httpRequester: OkHttpRequester = OkHttpRequester()

    private val hashProvider: HashProvider = HashProvider()

    private val apiConstants: ApiConstants = ApiConstants()

    private val jsonParser: GsonParser = GsonParser()

    private val userSession: UserSession = UserSession(context)

    private val headers: MutableMap<String, String> = HashMap()

    override fun createRaiting(
        value: Int,
        description: String,
        receiverUserId: Int,
        taskId: Int,
        receiverUserRoleId: Int
    ): Observable<Boolean> {
        val raiting: MutableMap<String, String> = HashMap()
        raiting["receiverUserId"] = receiverUserId.toString()
        raiting["taskId"] = taskId.toString()
        raiting["receiverUserRoleId"] = receiverUserRoleId.toString()
        raiting["description"] = description
        raiting["value"] = value.toString() + ""
        return httpRequester
            .post(apiConstants.createRaitingUrl(), raiting, headers)
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                true
            }
    }

    init {
        headers["authToken"] = userSession.id.toString() + ":" + userSession.accessToken
    }
}