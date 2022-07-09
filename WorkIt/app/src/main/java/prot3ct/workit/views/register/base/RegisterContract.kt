package prot3ct.workit.views.register.base;

import prot3ct.workit.base.BaseView;

public interface RegisterContract {
    interface View extends BaseView<Presenter> {
        void showLoginActivity();

        void notifySuccessful(String msg);

        void notifyError(String message);

        void showDialogforLoading();

        void dismissDialog();
    }

    interface Presenter {
        void registerUser(String email, String fullName, String password);
    }
}
