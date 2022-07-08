package prot3ct.workit.views.create_task;

import android.content.Context;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import prot3ct.workit.data.remote.LocationData;
import prot3ct.workit.data.remote.TaskData;
import prot3ct.workit.views.create_task.base.CreateTaskContract;

public class CreateTaskPresenter implements CreateTaskContract.Presenter {
    private CreateTaskContract.View view;
    private TaskData taskData;
    private LocationData locationData;

    public CreateTaskPresenter(CreateTaskContract.View view, Context context) {
        this.view = view;
        this.taskData = new TaskData(context);
        this.locationData = new LocationData();
    }

    @Override
    public void createTask(String title, String startDate, String length,
                           String description, String city, String address, String reward ) {
        taskData.createTask(title, startDate, length, description, city, address, reward)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                view.showDialogforLoading();
                            }

                            @Override
                            public void onNext(Boolean value) {
                                view.notifySuccessful();
                                view.showListJobsActivity();
                                view.dismissDialog();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.notifyError("Error ocurred when trying to create task. Please try again.");
                                view.dismissDialog();
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }

    @Override
    public void checkIfLocationExists(String location) {
        locationData.checkIfLocationExists(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean result) {
                                if (result) {
                                    view.createTask();
                                }
                                else {
                                    view.notifyInvalidLocation();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                view.notifyError("Error validating location.");
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }
}
