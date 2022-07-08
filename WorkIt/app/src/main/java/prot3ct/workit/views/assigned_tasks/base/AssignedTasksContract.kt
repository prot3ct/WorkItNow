package prot3ct.workit.views.assigned_tasks.base;

import java.util.List;

import prot3ct.workit.base.BaseView;
import prot3ct.workit.view_models.AssignedTasksListViewModel;

public interface AssignedTasksContract {
    interface View extends BaseView<Presenter> {
        void notifySuccessful(String message);

        void notifyError(String errorMessage);

        void filterTask(String query);

        void setupTasksAdapter(final List<AssignedTasksListViewModel> tasks);
    }

    interface Presenter {
        void getAssignedTasks();

        void removeAssignedUser(int taskId);
    }
}
