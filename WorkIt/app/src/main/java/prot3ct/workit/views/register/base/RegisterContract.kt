package prot3ct.workit.views.register.base

import prot3ct.workit.base.BaseView

interface RegisterContract {
    interface View : BaseView<Presenter> {
        fun showLoginActivity()
        fun notifySuccessful(msg: String)
        fun notifyError(message: String)
        fun showDialogforLoading()
        fun dismissDialog()
    }

    interface Presenter {
        fun registerUser(email: String, fullName: String, password: String)
    }
}