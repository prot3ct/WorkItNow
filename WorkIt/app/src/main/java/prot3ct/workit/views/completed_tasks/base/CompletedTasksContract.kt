package prot3ct.workit.views.completed_tasks.base;

import java.util.List;

import prot3ct.workit.base.BaseView;
import prot3ct.workit.view_models.CompletedTasksListViewModel;

public interface CompletedTasksContract {
    interface View extends BaseView<Presenter> {
        void showDialogForLoading();

        void dismissDialog();

        void setupTasksAdapter(final List<CompletedTasksListViewModel> tasks);

        void notifyError(String msg);

        void notifySuccessful(String msg);

        void filterTask(String query);

        void showDialog();

        void postRaiting(int value, String descrption);

        void updateSelectedInfo(int taskId, int supervisorId, int taskerId);

        int getLoggedInUserId();
    }

    interface Presenter {
        void getCompletedTasks();

        void createRating(int value, String description, int receiverUserId, int taskId, int receiverUserRoleId);

        int getLoggedInUserId();
    }
}
