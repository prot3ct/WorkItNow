package prot3ct.workit.views.create_task.base

import prot3ct.workit.base.BaseView

interface CreateTaskContract {
    interface View : BaseView<Presenter> {
        fun showListJobsActivity()
        fun notifySuccessful()
        fun showDialogforLoading()
        fun dismissDialog()
        fun createTask()
        fun notifyError(errorMessage: String)
        fun notifyInvalidLocation()
    }

    interface Presenter {
        fun createTask(
            title: String, startDate: String, length: String,
            description: String, city: String, address: String, reward: String
        )

        fun checkIfLocationExists(location: String)
    }
}