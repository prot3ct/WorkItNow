package prot3ct.workit.views.list_dialogs.base;

import java.util.List;

import prot3ct.workit.base.BaseView;
import prot3ct.workit.view_models.DialogsListViewModel;
import prot3ct.workit.view_models.TaskDetailViewModel;

public interface ListDialogsContract {
    interface View extends BaseView<ListDialogsContract.Presenter> {
        void notifySuccessful();

        void showDialogforLoading();

        void updateDialogs(List<DialogsListViewModel> dialogs);

        void dismissDialog();

        void notifyError(String errorMessage);
    }

    interface Presenter {
        void getDialogs();

        int getLoggedInUserId();
    }
}
