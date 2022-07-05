package prot3ct.workit.views.edit_profile.base;

import prot3ct.workit.base.BaseView;
import prot3ct.workit.view_models.ProfileDetailsViewModel;

public interface EditProfileContract {
    interface View extends BaseView<Presenter> {
        void notifyError(String errorMessage);

        void updateProfile(ProfileDetailsViewModel profileDetails);

        void showDialogforLoading();

        void notifySuccessful(String msg);

        void showProfileActivity();

        void dismissDialog();
    }

    interface Presenter {
        void updateProfile(String fullName, String phone, String profilePictureAsString);

        void getProfileDetails(int userId);
    }
}
