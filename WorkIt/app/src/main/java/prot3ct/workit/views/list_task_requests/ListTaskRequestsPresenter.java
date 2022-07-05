package prot3ct.workit.views.list_task_requests;

import android.content.Context;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import prot3ct.workit.data.remote.TaskRequestData;
import prot3ct.workit.data.remote.UserData;
import prot3ct.workit.view_models.ProfileDetailsViewModel;
import prot3ct.workit.view_models.TaskRequestListViewModel;
import prot3ct.workit.views.list_task_requests.base.ListTaskRequestContract;

public class ListTaskRequestsPresenter implements  ListTaskRequestContract.Presenter {
    private ListTaskRequestContract.View view;
    private TaskRequestData taskRequestData;
    private UserData userData;

    public ListTaskRequestsPresenter(ListTaskRequestContract.View view, Context context) {
        this.view = view;
        this.taskRequestData = new TaskRequestData(context);
        this.userData = new UserData(context);
    }

    @Override
    public void getTaskRequests(int taskId) {
        taskRequestData.getAllTaskRequestsForTask(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<List<TaskRequestListViewModel>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(List<TaskRequestListViewModel> taskRequests) {
                                view.setupTaskRequestsAdapter(taskRequests);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.notifyError("Error loading task requests.");
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }

    @Override
    public void acceptTaskRequest(int taskRequestId, int taskRequestStatusId) {
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
                        view.notifySuccessful("User assigned successfully");
                        view.showMyTasksActivty();
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
}
