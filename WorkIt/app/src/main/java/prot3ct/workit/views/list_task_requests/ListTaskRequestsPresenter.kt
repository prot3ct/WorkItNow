package prot3ct.workit.views.list_task_requests

import android.content.Context
import io.reactivex.Observer
import prot3ct.workit.data.remote.TaskRequestData
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import prot3ct.workit.data.remote.UserData
import prot3ct.workit.view_models.TaskRequestListViewModel
import prot3ct.workit.views.list_task_requests.base.ListTaskRequestContract

class ListTaskRequestsPresenter(private val view: ListTaskRequestContract.View, context: Context) :
    ListTaskRequestContract.Presenter {
    private val taskRequestData: TaskRequestData
    private val userData: UserData
    override fun getTaskRequests(taskId: Int) {
        taskRequestData.getAllTaskRequestsForTask(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<List<TaskRequestListViewModel>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(taskRequests: List<TaskRequestListViewModel>) {
                        view.setupTaskRequestsAdapter(taskRequests)
                    }

                    override fun onError(e: Throwable) {
                        view.notifyError("Error loading task requests.")
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                })
    }

    override fun acceptTaskRequest(taskRequestId: Int, taskRequestStatusId: Int) {
        taskRequestData.updateTaskRequest(taskRequestId, taskRequestStatusId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(taskRequests: Boolean) {
                        view.notifySuccessful("User assigned successfully")
                        view.showMyTasksActivty()
                    }

                    override fun onError(e: Throwable) {
                        view.notifyError("Error submiting request.")
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                })
    }

    override fun declineTaskRequest(taskRequestId: Int, taskRequestStatusId: Int) {
        taskRequestData.updateTaskRequest(taskRequestId, taskRequestStatusId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(taskRequests: Boolean) {}
                    override fun onError(e: Throwable) {
                        view.notifyError("Error declining request.")
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                })
    }

    init {
        taskRequestData = TaskRequestData(context)
        userData = UserData(context)
    }
}