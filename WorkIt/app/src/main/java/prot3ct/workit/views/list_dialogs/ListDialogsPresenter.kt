package prot3ct.workit.views.list_dialogs

import android.content.Context
import io.reactivex.Observer
import prot3ct.workit.data.remote.DialogData
import prot3ct.workit.data.remote.AuthData
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import prot3ct.workit.view_models.DialogsListViewModel
import prot3ct.workit.views.list_dialogs.base.ListDialogsContract

class ListDialogsPresenter(private val view: ListDialogsContract.View, context: Context) :
    ListDialogsContract.Presenter {
    private val dialogData: DialogData
    private val authData: AuthData
    override val dialogs: Unit
        get() {
            dialogData.dialogs
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    object : Observer<List<DialogsListViewModel>> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onNext(dialogs: List<DialogsListViewModel>) {
                            view.updateDialogs(dialogs)
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

    init {
        dialogData = DialogData(context)
        authData = AuthData(context)
    }
}