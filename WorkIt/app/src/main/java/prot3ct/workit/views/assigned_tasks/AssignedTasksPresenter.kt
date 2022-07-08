package prot3ct.workit.views.assigned_tasks

import android.content.Context
import io.reactivex.Observer
import prot3ct.workit.data.remote.AuthData
import prot3ct.workit.data.remote.TaskData
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import prot3ct.workit.view_models.AssignedTasksListViewModel
import prot3ct.workit.views.assigned_tasks.base.AssignedTasksContract

class AssignedTasksPresenter(private val view: AssignedTasksContract.View, context: Context?) :
    AssignedTasksContract.Presenter {
    private val authData: AuthData
    private val taskData: TaskData
    override fun getAssignedTasks() {
        taskData.assignedTasks
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<List<AssignedTasksListViewModel>> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(tasks: List<AssignedTasksListViewModel>) {
                        view.setupTasksAdapter(tasks)
                    }

                    override fun onError(e: Throwable) {
                        view.notifyError("Error occurred when loading tasks.")
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                })
    }

    override fun removeAssignedUser(taskId: Int) {
        taskData.removeAssignedUser(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean?> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(taskRequests: Boolean?) {
                        view.notifySuccessful("Removed task successfully")
                    }

                    override fun onError(e: Throwable) {
                        view.notifyError("Error submiting request.")
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                })
    }

    init {
        authData = AuthData(context!!)
        taskData = TaskData(context)
    }
}