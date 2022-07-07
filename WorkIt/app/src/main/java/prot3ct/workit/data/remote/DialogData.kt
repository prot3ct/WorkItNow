package prot3ct.workit.data.remote

import android.content.Context
import prot3ct.workit.utils.OkHttpRequester
import prot3ct.workit.config.ApiConstants
import prot3ct.workit.utils.GsonParser
import prot3ct.workit.data.local.UserSession
import prot3ct.workit.view_models.DialogsListViewModel
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import java.lang.Error
import kotlin.collections.HashMap

class DialogData(context: Context) {
    private val httpRequester: OkHttpRequester = OkHttpRequester()

    private val apiConstants: ApiConstants = ApiConstants()

    private val jsonParser: GsonParser = GsonParser()

    private val userSession: UserSession = UserSession(context)

    private val headers: MutableMap<String, String> = HashMap()

    fun createDialog(userId: Int): Observable<String> {
        val dialogDetails: MutableMap<String, String> = HashMap()
        dialogDetails["user1Id"] = userSession.id.toString() + ""
        dialogDetails["user2Id"] = userId.toString() + ""
        return httpRequester
            .post(apiConstants.createDialog(), dialogDetails, headers)
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode() || iHttpResponse.code == apiConstants.reponseServerErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                iHttpResponse.body
            }
    }

    val dialogs: Observable<List<DialogsListViewModel>>
        get() = httpRequester[apiConstants.getDialogs(userSession.id), headers]
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                val responseBody = iHttpResponse.body
                jsonParser.fromJson(
                    responseBody,
                    object : TypeToken<List<DialogsListViewModel?>?>() {}.type
                )
            }

    init {
        headers["authToken"] = userSession.id.toString() + ":" + userSession.accessToken
    }
}