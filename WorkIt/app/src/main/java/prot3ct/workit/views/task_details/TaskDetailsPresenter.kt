package prot3ct.workit.views.task_details

import android.content.Context
import io.reactivex.Observer
import prot3ct.workit.data.remote.TaskRequestData
import prot3ct.workit.data.remote.TaskData
import prot3ct.workit.data.remote.LocationData
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import prot3ct.workit.models.Location
import prot3ct.workit.view_models.TaskDetailViewModel
import prot3ct.workit.view_models.IsUserAssignableToTaskViewModel
import prot3ct.workit.views.task_details.base.TaskDetailsContract

class TaskDetailsPresenter(private val view: TaskDetailsContract.View, context: Context) :
    TaskDetailsContract.Presenter {

    private val taskRequestData: TaskRequestData
    private val taskData: TaskData
    private val locationData: LocationData

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

    override fun getCanAssignToTask(taskId: Int) {
        taskData.canAssignToTask(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<IsUserAssignableToTaskViewModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(canAssignToTask: IsUserAssignableToTaskViewModel) {
                        view.updateButton(canAssignToTask)
                    }

                    override fun onError(e: Throwable) {
                        view.notifyError("Error getting if possible to assign.")
                    }

                    override fun onComplete() {}
                })
    }

    override fun createTaskRequest(taskId: Int) {
        taskRequestData.createTaskRequest(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(result: Boolean) {
                        view.notifySuccessful("Request has been sent successfully")
                    }

                    override fun onError(e: Throwable) {
                        view.notifyError("Error ocurred when sending request. Please try again.")
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                })
    }

    override fun getLatLng(location: String) {
        locationData.getLatLng(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Location> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(location: Location) {
                        view.updateMap(location.lat, location.lng)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.notifyError("Error ocurred when sending request. Please try again.")
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

    override fun removeAssignedUser(taskId: Int) {
        taskData.removeAssignedUser(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(taskRequests: Boolean) {
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
        taskRequestData = TaskRequestData(context)
        taskData = TaskData(context)
        locationData = LocationData()
    }
}