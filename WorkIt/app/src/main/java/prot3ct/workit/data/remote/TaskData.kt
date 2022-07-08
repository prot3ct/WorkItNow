package prot3ct.workit.data.remote

import android.content.Context
import prot3ct.workit.data.remote.base.TaskDataContract
import prot3ct.workit.utils.OkHttpRequester
import prot3ct.workit.config.ApiConstants
import prot3ct.workit.utils.GsonParser
import prot3ct.workit.data.local.UserSession
import prot3ct.workit.view_models.TaskDetailViewModel
import prot3ct.workit.view_models.AvailableTasksListViewModel
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import prot3ct.workit.view_models.AssignedTasksListViewModel
import prot3ct.workit.view_models.MyTasksListViewModel
import prot3ct.workit.view_models.CompletedTasksListViewModel
import prot3ct.workit.view_models.IsUserAssignableToTaskViewModel
import java.lang.Error
import kotlin.collections.HashMap

class TaskData(context: Context) : TaskDataContract {

    private val httpRequester: OkHttpRequester = OkHttpRequester()

    private val apiConstants: ApiConstants = ApiConstants()

    private val jsonParser: GsonParser = GsonParser()

    private val userSession: UserSession = UserSession(context)

    private val headers: MutableMap<String, String> = HashMap()

    override fun createTask(
        title: String, startDate: String, length: String,
        description: String, city: String, address: String, reward: String
    ): Observable<Boolean> {
        val taskDetails: MutableMap<String, String> = HashMap()
        taskDetails["title"] = title
        taskDetails["startDate"] = startDate
        taskDetails["length"] = length
        taskDetails["description"] = description
        taskDetails["city"] = city
        taskDetails["address"] = address
        taskDetails["reward"] = reward
        taskDetails["creatorEmail"] = userSession.email!!.replace("\"".toRegex(), "")
        return httpRequester
            .post(apiConstants.createTaskUrl(), taskDetails, headers)
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode() || iHttpResponse.code == apiConstants.reponseServerErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                true
            }
    }

    override fun updateTask(
        taskId: Int, title: String, startDate: String, length: String,
        description: String, city: String, address: String, reward: String
    ): Observable<Boolean> {
        val taskDetails: MutableMap<String, String> = HashMap()
        taskDetails["id"] = taskId.toString() + ""
        taskDetails["title"] = title
        taskDetails["startDate"] = startDate
        taskDetails["length"] = length
        taskDetails["description"] = description
        taskDetails["city"] = city
        taskDetails["address"] = address
        taskDetails["reward"] = reward
        return httpRequester
            .put(apiConstants.updateTaskUrl(taskId), taskDetails, headers.toMutableMap())
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode() || iHttpResponse.code == apiConstants.reponseServerErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                true
            }
    }

    override fun getTaskDetails(taskId: Int): Observable<TaskDetailViewModel> {
        return httpRequester[apiConstants.getTaskDetailsUrl(taskId), headers]
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                val responseBody = iHttpResponse.body
                jsonParser.fromJson(responseBody, TaskDetailViewModel::class.java)
            }
    }

    override fun deleteTask(taskId: Int): Observable<Boolean> {
        return httpRequester
            .delete(apiConstants.deleteTaskUrl(taskId), headers.toMutableMap())
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                true
            }
    }

    override fun getAvailableTasks(
        page: Int,
        search: String
    ): Observable<List<AvailableTasksListViewModel>> {
        return httpRequester[apiConstants.getAvailableTasks(userSession.id, page), search, headers.toMutableMap()]
            .map { iHttpResponse ->
                if (iHttpResponse.code != apiConstants.responseSuccessCode()) {
                    throw Error(iHttpResponse.message)
                }
                val responseBody = iHttpResponse.body
                jsonParser.fromJson(
                    responseBody,
                    object : TypeToken<List<AvailableTasksListViewModel?>?>() {}.type
                )
            }
    }

    override val assignedTasks: Observable<List<AssignedTasksListViewModel>>
        get() = httpRequester[apiConstants.getAssignedTasksUrl(userSession.id), headers]
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                val responseBody = iHttpResponse.body
                jsonParser.fromJson(
                    responseBody ?: "",
                    object : TypeToken<List<AssignedTasksListViewModel>>() {}.type
                )
            }
    override val myTasks: Observable<List<MyTasksListViewModel>>
        get() = httpRequester[apiConstants.getMyTasks(userSession.id), headers]
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                val responseBody = iHttpResponse.body
                jsonParser.fromJson(
                    responseBody ?: "",
                    object : TypeToken<List<MyTasksListViewModel?>?>() {}.type
                )
            }
    override val completedTasks: Observable<List<CompletedTasksListViewModel>>
        get() = httpRequester[apiConstants.getCompletedTasksUrl(userSession.id), headers]
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                val responseBody = iHttpResponse.body
                jsonParser.fromJson(
                    responseBody,
                    object : TypeToken<List<CompletedTasksListViewModel?>?>() {}.type
                )
            }

    override fun canAssignToTask(taskId: Int): Observable<IsUserAssignableToTaskViewModel> {
        val taskDetails: MutableMap<String, String> = HashMap()
        taskDetails["taskId"] = taskId.toString()
        taskDetails["userId"] = userSession.id.toString()
        return httpRequester
            .post(apiConstants.isUserAssignableToTask(), taskDetails, headers)
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                val responseBody = iHttpResponse.body
                jsonParser.fromJson(responseBody, IsUserAssignableToTaskViewModel::class.java)
            }
    }

    override fun removeAssignedUser(taskId: Int): Observable<Boolean> {
        val taskDetails: MutableMap<String, String> = HashMap()
        taskDetails["taskId"] = taskId.toString()
        return httpRequester
            .put(apiConstants.updateAssignedUser(taskId), taskDetails, headers.toMutableMap())
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