package prot3ct.workit.views.profile.base;

import prot3ct.workit.base.BaseView;
import prot3ct.workit.view_models.ProfileDetailsViewModel;

public interface ProfileContract {
    interface View extends BaseView<Presenter> {
        void notifyError(String errorMessage);

        void updateProfile(ProfileDetailsViewModel profileDetails);

        void showEditProfileActivity();

        void showDialogforLoading();

        void showChatActivity(int dialogId);

        void dismissDialog();
    }

    interface Presenter {
        void getProfileDetails(int userId);

        void createDialog(int userId);
    }
}
