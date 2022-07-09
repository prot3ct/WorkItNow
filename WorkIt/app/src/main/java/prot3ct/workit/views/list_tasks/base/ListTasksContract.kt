package prot3ct.workit.views.list_tasks.base

import prot3ct.workit.base.BaseView
import prot3ct.workit.view_models.AvailableTasksListViewModel

interface ListTasksContract {
    interface View : BaseView<Presenter> {
        fun showCreateJobActivity()
        fun showLoginActivity()
        fun notifySuccessful(message: String)
        fun filterTask(query: String)
        fun updateTasks(tasks: List<AvailableTasksListViewModel>)
        fun notifyError(msg: String)
        fun showEndProgressBar()
        fun hideEndProgressBar()
    }

    interface Presenter {
        fun getAllTasks(page: Int)
        fun getSearchedAvailableTasks(page: Int, search: String)
    }
}