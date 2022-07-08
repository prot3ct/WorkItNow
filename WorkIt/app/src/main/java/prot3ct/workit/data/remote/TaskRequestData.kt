package prot3ct.workit.data.remote

import android.content.Context
import prot3ct.workit.data.remote.base.TaskRequestDataContract
import prot3ct.workit.utils.OkHttpRequester
import prot3ct.workit.config.ApiConstants
import prot3ct.workit.utils.GsonParser
import prot3ct.workit.data.local.UserSession
import prot3ct.workit.view_models.TaskRequestListViewModel
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import java.lang.Error
import java.util.HashMap

class TaskRequestData(context: Context) : TaskRequestDataContract {
    private val httpRequester: OkHttpRequester = OkHttpRequester()
    private val apiConstants: ApiConstants = ApiConstants()
    private val jsonParser: GsonParser = GsonParser()
    private val userSession: UserSession = UserSession(context)
    private val headers: MutableMap<String, String> = HashMap()
    override fun createTaskRequest(taskId: Int): Observable<Boolean> {
        val taskRequest: MutableMap<String, String> = HashMap()
        taskRequest["taskId"] = Integer.toString(taskId)
        taskRequest["userId"] = Integer.toString(userSession.id)
        return httpRequester
            .post(apiConstants.createTaskRequestUrl(), taskRequest, headers)
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode() || iHttpResponse.code == apiConstants.reponseServerErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                true
            }
    }

    override fun updateTaskRequest(taskRequestId: Int, status: Int): Observable<Boolean> {
        val body: MutableMap<String, String> = HashMap()
        body["taskRequestId"] = taskRequestId.toString()
        body["requestStatusId"] = status.toString()
        return httpRequester
            .put(apiConstants.updateTaskRequestUrl(taskRequestId), body, headers.toMutableMap())
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.body)
                }
                true
            }
    }

    override fun getAllTaskRequestsForTask(taskId: Int): Observable<List<TaskRequestListViewModel>> {
        return httpRequester[apiConstants.getRequestsForTaskUrl(taskId), headers]
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                val responseBody = iHttpResponse.body
                jsonParser.fromJson(
                    responseBody,
                    object : TypeToken<List<TaskRequestListViewModel?>?>() {}.type
                )
            }
    }

    init {
        headers["authToken"] = userSession.id.toString() + ":" + userSession.accessToken
    }
}