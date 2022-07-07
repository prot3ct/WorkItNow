package prot3ct.workit.config

import prot3ct.workit.config.base.ApiConstantsContract

class ApiConstants : ApiConstantsContract {
    override fun loginUrl(): String = "$API_URL/auth/login"

    override fun registerUrl() = "$API_URL/auth/register"

    override fun autoLoginUserUrl() = "$API_URL/auth/autologin"


    override fun createTaskUrl() = "$API_URL/tasks"


    override fun updateTaskUrl(taskId: Int) =
        "$API_URL/tasks/$taskId"


    override fun getTaskDetailsUrl(taskId: Int) =
        "$API_URL/tasks/$taskId"


    override fun deleteTaskUrl(taskId: Int) =
        "$API_URL/tasks/$taskId"


    override fun getAvailableTasks(userId: Int, page: Int) =
        "$API_URL/users/$userId/available-tasks/page/$page"


    override fun getAssignedTasksUrl(userId: Int) = "$API_URL/users/$userId/assigned-tasks"


    override fun getCompletedTasksUrl(userId: Int) =
        "$API_URL/users/$userId/completed-tasks"


    override fun getMyTasks(userId: Int) = "$API_URL/users/$userId/my-tasks"


    override fun createTaskRequestUrl() = "$API_URL/requests"


    override fun updateTaskRequestUrl(requestId: Int) = "$API_URL/requests/$requestId"


    override fun getRequestsForTaskUrl(taskId: Int) = "$API_URL/tasks/$taskId/requests"


    override fun deleteTaskRequestUrl(taskRequestId: Int) =
        "$API_URL/requests/$taskRequestId/delete"


    override fun createRaitingUrl() = "$API_URL/raitings"


    override fun updateProfile(userId: Int) = "$API_URL/users/$userId"


    override fun getProfileDetailsUrl(userId: Int) = "$API_URL/users/$userId"


    override fun isUserAssignableToTask() = "$API_URL/tasks/can-assign"

    override fun getLocationLatLngUrl(location: String) =
        "https://maps.googleapis.com/maps/api/geocode/json?address=$location&key=AIzaSyA4t0Wp6n0os2wVPs3JRoSnDDJf49JVgFM"


    override fun updateAssignedUser(taskId: Int) = "$API_URL/tasks/$taskId/assigned-user"


    override fun createDialog() = "$API_URL/dialogs/"


    override fun getDialogs(userId: Int) = API_URL + "/users/" + userId + "/dialogs"


    override fun createMessage(dialogId: Int) = API_URL + "/dialogs/" + dialogId + "/messages"


    override fun getMessages(dialogId: Int) = API_URL + "/dialogs/" + dialogId + "/messages"


    override fun responseSuccessCode() = RESPONSE_SUCCESS_CODE


    override fun responseErrorCode() = RESPONSE_ERROR_CODE


    override fun reponseServerErrorCode() = RESPONSE_SERVER_ERROR_CODE


    companion object {
        private const val API_URL = "http://workit.azurewebsites.net/api"
        private const val RESPONSE_SUCCESS_CODE = 200
        private const val RESPONSE_ERROR_CODE = 404
        private const val RESPONSE_SERVER_ERROR_CODE = 500
    }
}