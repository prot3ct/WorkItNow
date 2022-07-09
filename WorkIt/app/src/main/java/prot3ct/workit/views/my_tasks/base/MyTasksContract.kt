package prot3ct.workit.views.my_tasks.base

import prot3ct.workit.base.BaseView
import prot3ct.workit.view_models.MyTasksListViewModel

interface MyTasksContract {
    interface View : BaseView<Presenter> {
        fun showCreateJobActivity()
        fun notifySuccessful(message: String)
        fun filterTask(query: String)
        fun notifyError(errorMessage: String)
        fun setupTasksAdapter(tasks: List<MyTasksListViewModel>)
    }

    interface Presenter {
        val myTasks: Unit
        fun deleteTask(taskId: Int)
    }
}