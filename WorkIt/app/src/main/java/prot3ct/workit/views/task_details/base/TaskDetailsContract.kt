package prot3ct.workit.views.task_details.base

import prot3ct.workit.base.BaseView
import prot3ct.workit.view_models.TaskDetailViewModel
import prot3ct.workit.view_models.IsUserAssignableToTaskViewModel

interface TaskDetailsContract {
    interface View : BaseView<Presenter> {
        fun updateTask(taskDetails: TaskDetailViewModel)
        fun notifySuccessful(message: String)
        fun notifyError(errorMessage: String)
        fun updateButton(canAssignToTask: IsUserAssignableToTaskViewModel)
        fun showDialogforLoading()
        fun updateMap(lat: Double, lng: Double)
        fun dismissDialog()
    }

    interface Presenter {
        fun getTaskDetails(taskId: Int)
        fun getCanAssignToTask(taskId: Int)
        fun createTaskRequest(taskId: Int)
        fun getLatLng(location: String)
        fun declineTaskRequest(taskRequestId: Int, taskRequestStatusId: Int)
        fun removeAssignedUser(taskId: Int)
    }
}