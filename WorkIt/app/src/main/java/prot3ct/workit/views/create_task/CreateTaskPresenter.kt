package prot3ct.workit.views.create_task

import android.content.Context
import io.reactivex.Observer
import prot3ct.workit.data.remote.TaskData
import prot3ct.workit.data.remote.LocationData
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import prot3ct.workit.views.create_task.base.CreateTaskContract

class CreateTaskPresenter(private val view: CreateTaskContract.View, context: Context?) :
    CreateTaskContract.Presenter {
    private val taskData: TaskData
    private val locationData: LocationData
    override fun createTask(
        title: String, startDate: String, length: String,
        description: String, city: String, address: String, reward: String
    ) {
        taskData.createTask(title, startDate, length, description, city, address, reward)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean?> {
                    override fun onSubscribe(d: Disposable) {
                        view.showDialogforLoading()
                    }

                    override fun onNext(value: Boolean?) {
                        view.notifySuccessful()
                        view.showListJobsActivity()
                        view.dismissDialog()
                    }

                    override fun onError(e: Throwable) {
                        view.notifyError("Error ocurred when trying to create task. Please try again.")
                        view.dismissDialog()
                    }

                    override fun onComplete() {}
                })
    }

    override fun checkIfLocationExists(location: String) {
        locationData.checkIfLocationExists(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(result: Boolean) {
                        if (result) {
                            view.createTask()
                        } else {
                            view.notifyInvalidLocation()
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.notifyError("Error validating location.")
                    }

                    override fun onComplete() {}
                })
    }

    init {
        taskData = TaskData(context!!)
        locationData = LocationData()
    }
}