package prot3ct.workit.views.create_task.base;

import prot3ct.workit.base.BaseView;

public interface CreateTaskContract {
    interface View extends BaseView<CreateTaskContract.Presenter> {
        void showListJobsActivity();

        void notifySuccessful();

        void showDialogforLoading();

        void dismissDialog();

        void createTask();

        void notifyError(String errorMessage);

        void notifyInvalidLocation();
    }

    interface Presenter {
        void createTask(String title, String startDate, String length,
                        String description, String city, String address, String reward);

        void checkIfLocationExists(String location);
    }
}
