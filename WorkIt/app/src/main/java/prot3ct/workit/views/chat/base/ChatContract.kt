package prot3ct.workit.views.chat.base

import prot3ct.workit.base.BaseView
import prot3ct.workit.view_models.ListMessagesViewModel

interface ChatContract {
    interface View : BaseView<Presenter> {
        fun notifySuccessful()
        fun showDialogForLoading()
        fun dismissDialog()
        fun updateChat(messages: List<ListMessagesViewModel>)
        fun notifyError(errorMessage: String)
    }

    interface Presenter {
        fun createMessage(text: String, authorId: Int, dialogId: Int, createdAt: String)
        val loggedInUserId: Int
        val loggedInUserName: String
        fun getMessages(dialogId: Int)
    }
}