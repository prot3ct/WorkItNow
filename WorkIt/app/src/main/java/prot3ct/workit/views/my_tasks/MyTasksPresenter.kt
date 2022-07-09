package prot3ct.workit.views.my_tasks

import android.content.Context
import io.reactivex.Observer
import prot3ct.workit.data.remote.AuthData
import prot3ct.workit.data.remote.TaskData
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import prot3ct.workit.view_models.MyTasksListViewModel
import prot3ct.workit.views.my_tasks.base.MyTasksContract

class MyTasksPresenter(private val view: MyTasksContract.View, context: Context) :
    MyTasksContract.Presenter {

    private val authData: AuthData
    private val taskData: TaskData

    override val myTasks: Unit
        get() {
            taskData.myTasks
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    object : Observer<List<MyTasksListViewModel>> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onNext(tasks: List<MyTasksListViewModel>) {
                            view.setupTasksAdapter(tasks)
                        }

                        override fun onError(e: Throwable) {
                            view.notifyError("Error ocurred loading tasks.")
                            e.printStackTrace()
                        }

                        override fun onComplete() {}
                    })
        }

    override fun deleteTask(taskId: Int) {
        taskData.deleteTask(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(result: Boolean) {
                        view.notifyError("Task deleted successfully.")
                    }

                    override fun onError(e: Throwable) {
                        view.notifyError("Error ocurred deleting task.")
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                })
    }

    init {
        authData = AuthData(context)
        taskData = TaskData(context)
    }
}