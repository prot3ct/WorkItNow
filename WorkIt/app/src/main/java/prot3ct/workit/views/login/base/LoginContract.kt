package prot3ct.workit.views.login.base

import prot3ct.workit.base.BaseView

interface LoginContract {
    interface View : BaseView<Presenter> {
        fun showListJobsActivity()
        fun showRegisterActivity()
        fun notifyError(errorMessage: String)
        fun showDialogforLoading()
        fun dismissDialog()
    }

    interface Presenter {
        fun loginUser(email: String, password: String)
        fun autoLoginUser()
        val isUserLoggedIn: Boolean
    }
}