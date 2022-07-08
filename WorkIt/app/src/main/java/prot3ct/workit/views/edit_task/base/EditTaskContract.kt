package prot3ct.workit.views.edit_task.base

import prot3ct.workit.base.BaseView
import prot3ct.workit.view_models.TaskDetailViewModel

interface EditTaskContract {
    interface View : BaseView<Presenter> {
        fun showListJobsActivity()
        fun updateTask(task: TaskDetailViewModel)
        fun notifySuccessful()
        fun showDialogforLoading()
        fun dismissDialog()
        fun notifyError(errorMessage: String)
    }

    interface Presenter {
        fun updateTask(
            taskId: Int, title: String, startDate: String, length: String,
            description: String, city: String, address: String, reward: String
        )

        fun getTaskDetails(taskId: Int)
    }
}