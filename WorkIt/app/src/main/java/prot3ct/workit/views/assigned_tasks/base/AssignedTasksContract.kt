package prot3ct.workit.views.assigned_tasks.base

import prot3ct.workit.base.BaseView
import prot3ct.workit.view_models.AssignedTasksListViewModel

interface AssignedTasksContract {
    interface View : BaseView<Presenter> {
        fun notifySuccessful(message: String)
        fun notifyError(errorMessage: String)
        fun filterTask(query: String)
        fun setupTasksAdapter(tasks: List<AssignedTasksListViewModel>)
    }

    interface Presenter {
        fun getAssignedTasks(): Unit
        fun removeAssignedUser(taskId: Int)
    }
}