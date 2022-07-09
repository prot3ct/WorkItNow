package prot3ct.workit.views.task_details;

import android.content.Context;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import prot3ct.workit.data.remote.TaskData;
import prot3ct.workit.data.remote.LocationData;
import prot3ct.workit.data.remote.TaskRequestData;
import prot3ct.workit.models.Location;
import prot3ct.workit.view_models.IsUserAssignableToTaskViewModel;
import prot3ct.workit.view_models.TaskDetailViewModel;
import prot3ct.workit.views.task_details.base.TaskDetailsContract;

public class TaskDetailsPresenter implements TaskDetailsContract.Presenter {
    private TaskDetailsContract.View view;
    private TaskRequestData taskRequestData;
    private TaskData taskData;
    private LocationData locationData;

    public TaskDetailsPresenter(TaskDetailsContract.View view, Context context) {
        this.view = view;
        this.taskRequestData = new TaskRequestData(context);
        this.taskData = new TaskData(context);
        this.locationData = new LocationData();
    }

    @Override
    public void getTaskDetails(int taskId) {
        taskData.getTaskDetails(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<TaskDetailViewModel>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(TaskDetailViewModel task) {
                                view.updateTask(task);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.notifyError("Error loading task.");
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }

    @Override
    public void getCanAssignToTask(int taskId) {
        taskData.canAssignToTask(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<IsUserAssignableToTaskViewModel>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(IsUserAssignableToTaskViewModel canAssignToTask) {
                                view.updateButton(canAssignToTask);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.notifyError("Error getting if possible to assign.");
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }

    @Override
    public void createTaskRequest(int taskId) {
        taskRequestData.createTaskRequest(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean result) {
                        view.notifySuccessful("Request has been sent successfully");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.notifyError("Error ocurred when sending request. Please try again.");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void getLatLng(String location) {
        locationData.getLatLng(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<Location>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Location location) {
                        view.updateMap(location.getLat(), location.getLng());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.notifyError("Error ocurred when sending request. Please try again.");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void declineTaskRequest(int taskRequestId, int taskRequestStatusId) {
        taskRequestData.updateTaskRequest(taskRequestId, taskRequestStatusId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean taskRequests) {
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.notifyError("Error declining request.");
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }

    @Override
    public void removeAssignedUser(int taskId) {
        taskData.removeAssignedUser(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean taskRequests) {
                                view.notifySuccessful("Removed task successfully");
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.notifyError("Error submiting request.");
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }
}
