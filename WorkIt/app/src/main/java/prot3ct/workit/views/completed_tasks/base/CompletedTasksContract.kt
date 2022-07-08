package prot3ct.workit.views.completed_tasks.base

import prot3ct.workit.base.BaseView
import prot3ct.workit.view_models.CompletedTasksListViewModel

interface CompletedTasksContract {
    interface View : BaseView<Presenter> {
        fun showDialogForLoading()
        fun dismissDialog()
        fun setupTasksAdapter(tasks: List<CompletedTasksListViewModel>)
        fun notifyError(msg: String)
        fun notifySuccessful(msg: String)
        fun filterTask(query: String)
        fun showDialog()
        fun postRaiting(value: Int, descrption: String)
        fun updateSelectedInfo(taskId: Int, supervisorId: Int, taskerId: Int)
        val loggedInUserId: Int
    }

    interface Presenter {
        val completedTasks: Unit
        fun createRating(
            value: Int,
            description: String,
            receiverUserId: Int,
            taskId: Int,
            receiverUserRoleId: Int
        )

        val loggedInUserId: Int
    }
}