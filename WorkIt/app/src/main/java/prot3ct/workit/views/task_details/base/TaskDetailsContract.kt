package prot3ct.workit.views.task_details.base;

import prot3ct.workit.base.BaseView;
import prot3ct.workit.view_models.IsUserAssignableToTaskViewModel;
import prot3ct.workit.view_models.TaskDetailViewModel;

public interface TaskDetailsContract {
    interface View extends BaseView<Presenter> {
        void updateTask(TaskDetailViewModel taskDetails);

        void notifySuccessful(String message);

        void notifyError(String errorMessage);

        void updateButton(IsUserAssignableToTaskViewModel canAssignToTask);

        void showDialogforLoading();

        void updateMap(double lat, double lng);

        void dismissDialog();
    }

    interface Presenter {
        void getTaskDetails(int taskId);

        void getCanAssignToTask(int taskId);

        void createTaskRequest(int taskId);

        void getLatLng(String location);

        void declineTaskRequest(int taskRequestId, int taskRequestStatusId);

        void removeAssignedUser(int taskId);
    }
}
