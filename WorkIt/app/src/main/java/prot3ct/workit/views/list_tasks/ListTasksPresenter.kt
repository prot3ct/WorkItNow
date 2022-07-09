package prot3ct.workit.views.list_tasks

import android.content.Context
import android.util.Log
import io.reactivex.Observer
import prot3ct.workit.data.remote.AuthData
import prot3ct.workit.data.remote.TaskData
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import prot3ct.workit.view_models.AvailableTasksListViewModel
import prot3ct.workit.views.list_tasks.base.ListTasksContract

class ListTasksPresenter(private val view: ListTasksContract.View, context: Context) :
    ListTasksContract.Presenter {
    private val authData: AuthData
    private val taskData: TaskData

    override fun getAllTasks(page: Int) {
        taskData.getAvailableTasks(page, "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<List<AvailableTasksListViewModel>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(tasks: List<AvailableTasksListViewModel>) {
                        view.updateTasks(tasks)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.notifyError("Error ocurred retrieving data.")
                        Log.d("CEKO13", "asdasd")
                    }

                    override fun onComplete() {}
                })
    }

    override fun getSearchedAvailableTasks(page: Int, search: String) {
        taskData.getAvailableTasks(page, search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<List<AvailableTasksListViewModel>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(tasks: List<AvailableTasksListViewModel>) {
                        Log.d("TASKS1", tasks.size.toString() + "")
                        view.updateTasks(tasks)
                    }

                    override fun onError(e: Throwable) {
                        view.notifyError("Error ocurred retrieving data.")
                    }

                    override fun onComplete() {}
                })
    }

    init {
        authData = AuthData(context)
        taskData = TaskData(context)
    }
}