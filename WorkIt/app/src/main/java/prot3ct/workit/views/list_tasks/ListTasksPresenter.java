package prot3ct.workit.views.list_tasks;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import prot3ct.workit.data.remote.TaskData;
import prot3ct.workit.data.remote.AuthData;
import prot3ct.workit.view_models.AvailableTasksListViewModel;
import prot3ct.workit.views.list_tasks.base.ListTasksContract;

public class ListTasksPresenter implements ListTasksContract.Presenter {
    private ListTasksContract.View view;
    private AuthData authData;
    private TaskData taskData;

    public ListTasksPresenter(ListTasksContract.View view, Context context) {
        this.view = view;
        this.authData = new AuthData(context);
        this.taskData = new TaskData(context);
    }

    @Override
    public void getAllTasks(int page) {
        taskData.getAvailableTasks(page, "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<List<AvailableTasksListViewModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<AvailableTasksListViewModel> tasks) {
                        view.updateTasks(tasks);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.notifyError("Error ocurred retrieving data.");
                        Log.d("CEKO13", "asdasd");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void getSearchedAvailableTasks(int page, String search) {
        taskData.getAvailableTasks(page, search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<List<AvailableTasksListViewModel>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(List<AvailableTasksListViewModel> tasks) {
                                Log.d("TASKS1", tasks.size()+"");
                                view.updateTasks(tasks);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.notifyError("Error ocurred retrieving data.");
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }
}
