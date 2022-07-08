package prot3ct.workit.views.list_task_requests.base

import prot3ct.workit.base.BaseView
import prot3ct.workit.view_models.TaskRequestListViewModel

interface ListTaskRequestContract {
    interface View : BaseView<Presenter> {
        fun setupTaskRequestsAdapter(users: List<TaskRequestListViewModel>)
        fun showMyTasksActivty()
        fun notifySuccessful(message: String)
        fun notifyError(errorMessage: String)
    }

    interface Presenter {
        fun getTaskRequests(taskId: Int)
        fun acceptTaskRequest(taskRequestId: Int, taskRequestStatusId: Int)
        fun declineTaskRequest(taskRequestId: Int, taskRequestStatusId: Int)
    }
}