package prot3ct.workit.views.completed_tasks

import android.content.Context
import io.reactivex.Observer
import prot3ct.workit.data.remote.TaskData
import prot3ct.workit.data.remote.AuthData
import prot3ct.workit.data.remote.RaitingData
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import prot3ct.workit.view_models.CompletedTasksListViewModel
import prot3ct.workit.views.completed_tasks.base.CompletedTasksContract

class CompletedTasksPresenter(private val view: CompletedTasksContract.View, context: Context) :
    CompletedTasksContract.Presenter {
    private val taskData: TaskData
    private val authData: AuthData
    private val raitingData: RaitingData
    override val completedTasks: Unit
        get() {
            taskData.completedTasks
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    object : Observer<List<CompletedTasksListViewModel>> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onNext(tasks: List<CompletedTasksListViewModel>) {
                            view.setupTasksAdapter(tasks)
                        }

                        override fun onError(e: Throwable) {
                            view.notifyError("Error ocurred when retrieving data. Please try again.")
                        }

                        override fun onComplete() {}
                    })
        }
    override val loggedInUserId: Int
        get() = authData.loggedInUserId

    override fun createRating(
        value: Int,
        description: String,
        receiverUserId: Int,
        taskId: Int,
        receiverUserRoleId: Int
    ) {
        raitingData.createRaiting(value, description, receiverUserId, taskId, receiverUserRoleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean?> {
                    override fun onSubscribe(d: Disposable) {
                        view.showDialogForLoading()
                    }

                    override fun onNext(result: Boolean?) {
                        view.dismissDialog()
                        view.notifySuccessful("Raiting submitted successfully.")
                    }

                    override fun onError(e: Throwable) {
                        view.dismissDialog()
                        view.notifyError("Error ocurred when submitting raiting.")
                    }

                    override fun onComplete() {}
                })
    }

    init {
        taskData = TaskData(context)
        authData = AuthData(context)
        raitingData = RaitingData(context)
    }
}