package prot3ct.workit.views.list_dialogs.base

import prot3ct.workit.base.BaseView
import prot3ct.workit.view_models.DialogsListViewModel

interface ListDialogsContract {
    interface View : BaseView<Presenter> {
        fun notifySuccessful()
        fun showDialogforLoading()
        fun updateDialogs(dialogs: List<DialogsListViewModel>)
        fun dismissDialog()
        fun notifyError(errorMessage: String)
    }

    interface Presenter {
        val dialogs: Unit
        val loggedInUserId: Int
    }
}