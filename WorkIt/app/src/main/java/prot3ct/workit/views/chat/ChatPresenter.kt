package prot3ct.workit.views.chat

import android.content.Context
import io.reactivex.Observer
import prot3ct.workit.data.remote.MessageData
import prot3ct.workit.data.remote.AuthData
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import prot3ct.workit.view_models.ListMessagesViewModel
import prot3ct.workit.views.chat.base.ChatContract

class ChatPresenter(private val view: ChatContract.View, context: Context?) :
    ChatContract.Presenter {
    private val messageData: MessageData
    private val authData: AuthData
    override fun createMessage(text: String, authorId: Int, dialogId: Int, createdAt: String) {
        messageData.createMessage(text, authorId, dialogId, createdAt)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(id: Boolean) {}
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.notifyError("Error submiting message.")
                    }

                    override fun onComplete() {}
                })
    }

    override fun getMessages(dialogId: Int) {
        messageData.getMessages(dialogId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<List<ListMessagesViewModel>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(messages: List<ListMessagesViewModel>) {
                        view.updateChat(messages)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.notifyError("Error loading messages.")
                    }

                    override fun onComplete() {}
                })
    }

    override val loggedInUserId: Int
        get() = authData.loggedInUserId
    override val loggedInUserName: String
        get() = authData.loggedInUserName

    init {
        messageData = MessageData(context!!)
        authData = AuthData(context)
    }
}