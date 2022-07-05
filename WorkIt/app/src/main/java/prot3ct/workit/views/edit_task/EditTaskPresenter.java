package prot3ct.workit.views.edit_task;

import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import prot3ct.workit.data.remote.TaskData;
import prot3ct.workit.view_models.TaskDetailViewModel;
import prot3ct.workit.views.edit_task.base.EditTaskContract;

public class EditTaskPresenter implements EditTaskContract.Presenter {
    private EditTaskContract.View view;
    private TaskData taskData;

    public EditTaskPresenter(EditTaskContract.View view, Context context) {
        this.view = view;
        this.taskData = new TaskData(context);
    }

    @Override
    public void updateTask(int taskId, String title, String startDate, String length,
                           String description, String city, String address, String reward) {
        taskData.updateTask(taskId, title, startDate, length, description, city, address, reward)
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
                        view.notifyError("Error ocurred when trying to update task. Please try again.");
                        e.printStackTrace();
                        view.dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
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
}
