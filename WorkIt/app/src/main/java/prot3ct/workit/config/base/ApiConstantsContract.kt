package prot3ct.workit.config.base

interface ApiConstantsContract {
    fun loginUrl(): String

    fun registerUrl(): String

    fun autoLoginUserUrl(): String

    fun createTaskUrl(): String

    fun updateTaskUrl(taskId: Int): String

    fun getTaskDetailsUrl(taskId: Int): String

    fun deleteTaskUrl(taskId: Int): String

    fun getAvailableTasks(userId: Int, page: Int): String

    fun getAssignedTasksUrl(userId: Int): String

    fun getCompletedTasksUrl(userId: Int): String

    fun getMyTasks(userId: Int): String

    fun createTaskRequestUrl(): String

    fun updateTaskRequestUrl(requestId: Int): String

    fun getRequestsForTaskUrl(taskId: Int): String

    fun deleteTaskRequestUrl(taskRequestId: Int): String

    fun createRaitingUrl(): String

    fun updateProfile(userId: Int): String

    fun getProfileDetailsUrl(userId: Int): String

    fun isUserAssignableToTask(): String

    fun updateAssignedUser(taskId: Int): String

    fun createDialog(): String

    fun createMessage(dialogId: Int): String

    fun getMessages(dialogId: Int): String

    fun getDialogs(userId: Int): String

    fun getLocationLatLngUrl(location: String): String

    fun responseSuccessCode(): Int

    fun responseErrorCode(): Int

    fun reponseServerErrorCode(): Int
}