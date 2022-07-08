package prot3ct.workit.views.completed_tasks;

import android.content.Context;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import prot3ct.workit.data.remote.RaitingData;
import prot3ct.workit.data.remote.TaskData;
import prot3ct.workit.data.remote.AuthData;
import prot3ct.workit.view_models.CompletedTasksListViewModel;
import prot3ct.workit.views.completed_tasks.base.CompletedTasksContract;

public class CompletedTasksPresenter implements CompletedTasksContract.Presenter {
    private CompletedTasksContract.View view;
    private TaskData taskData;
    private AuthData authData;
    private RaitingData raitingData;

    public CompletedTasksPresenter(CompletedTasksContract.View view, Context context) {
        this.view = view;
        this.taskData = new TaskData(context);
        this.authData = new AuthData(context);
        this.raitingData = new RaitingData(context);
    }

    @Override
    public void getCompletedTasks() {
        taskData.getCompletedTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<List<CompletedTasksListViewModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<CompletedTasksListViewModel> tasks) {
                        view.setupTasksAdapter(tasks);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.notifyError("Error ocurred when retrieving data. Please try again.");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public int getLoggedInUserId() {
        return this.authData.getLoggedInUserId();
    }

    @Override
    public void createRating(int value, String description, int receiverUserId, int taskId, int receiverUserRoleId) {
        raitingData.createRaiting(value, description, receiverUserId, taskId, receiverUserRoleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                view.showDialogForLoading();
                            }

                            @Override
                            public void onNext(Boolean result) {
                                view.dismissDialog();
                                view.notifySuccessful("Raiting submitted successfully.");
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.dismissDialog();
                                view.notifyError("Error ocurred when submitting raiting.");
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }
}
