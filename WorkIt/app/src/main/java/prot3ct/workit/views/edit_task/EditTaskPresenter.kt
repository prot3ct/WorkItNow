package prot3ct.workit.views.edit_task

import android.content.Context
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import prot3ct.workit.data.remote.TaskData
import prot3ct.workit.view_models.TaskDetailViewModel
import prot3ct.workit.views.edit_task.base.EditTaskContract

class EditTaskPresenter(private val view: EditTaskContract.View, context: Context) :
    EditTaskContract.Presenter {
    private val taskData: TaskData
    override fun updateTask(
        taskId: Int, title: String, startDate: String, length: String,
        description: String, city: String, address: String, reward: String
    ) {
        taskData.updateTask(taskId, title, startDate, length, description, city, address, reward)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {
                        view.showDialogforLoading()
                    }

                    override fun onNext(value: Boolean) {
                        view.notifySuccessful()
                        view.showListJobsActivity()
                        view.dismissDialog()
                    }

                    override fun onError(e: Throwable) {
                        view.notifyError("Error ocurred when trying to update task. Please try again.")
                        e.printStackTrace()
                        view.dismissDialog()
                    }

                    override fun onComplete() {}
                })
    }

    override fun getTaskDetails(taskId: Int) {
        taskData.getTaskDetails(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<TaskDetailViewModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(task: TaskDetailViewModel) {
                        view.updateTask(task)
                    }

                    override fun onError(e: Throwable) {
                        view.notifyError("Error loading task.")
                    }

                    override fun onComplete() {}
                })
    }

    init {
        taskData = TaskData(context!!)
    }
}