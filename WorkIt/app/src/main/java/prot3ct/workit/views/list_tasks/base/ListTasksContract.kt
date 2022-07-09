package prot3ct.workit.views.list_tasks.base;

import android.view.View;

import java.util.List;

import prot3ct.workit.base.BaseView;
import prot3ct.workit.view_models.AvailableTasksListViewModel;

public interface ListTasksContract {
    interface View extends BaseView<Presenter> {
        void showCreateJobActivity();

        void showLoginActivity();

        void notifySuccessful(String message);

        void filterTask(String query);

        void updateTasks(final List<AvailableTasksListViewModel> tasks);

        void notifyError(String msg);

        void showEndProgressBar();

        void hideEndProgressBar();
    }

    interface Presenter {
        void getAllTasks(int page);

        void getSearchedAvailableTasks(int page, String search);
    }
}
