package prot3ct.workit.data.remote

import android.content.Context
import prot3ct.workit.utils.OkHttpRequester
import prot3ct.workit.config.ApiConstants
import prot3ct.workit.utils.GsonParser
import prot3ct.workit.data.local.UserSession
import prot3ct.workit.view_models.ListMessagesViewModel
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import java.lang.Error
import kotlin.collections.HashMap

class MessageData(context: Context) {
    private val httpRequester: OkHttpRequester = OkHttpRequester()
    private val apiConstants: ApiConstants = ApiConstants()
    private val jsonParser: GsonParser = GsonParser()
    private val userSession: UserSession = UserSession(context)
    private val headers: MutableMap<String, String> = HashMap()
    fun createMessage(
        text: String,
        authorId: Int,
        dialogId: Int,
        createdAt: String
    ): Observable<Boolean> {
        val messageDetails: MutableMap<String, String> = HashMap()
        messageDetails["text"] = text
        messageDetails["authorId"] = authorId.toString()
        messageDetails["dialogId"] = dialogId.toString()
        messageDetails["createdAt"] = createdAt
        return httpRequester
            .post(apiConstants.createMessage(dialogId), messageDetails, headers)
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode() || iHttpResponse.code == apiConstants.reponseServerErrorCode()) {
                    throw Error(iHttpResponse.body)
                }
                true
            }
    }

    fun getMessages(dialogId: Int): Observable<List<ListMessagesViewModel>> {
        return httpRequester[apiConstants.getMessages(dialogId), headers]
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                val responseBody = iHttpResponse.body
                jsonParser.fromJson(
                    responseBody,
                    object : TypeToken<List<ListMessagesViewModel?>?>() {}.type
                )
            }
    }

    init {
        headers["authToken"] = userSession.id.toString() + ":" + userSession.accessToken
    }
}