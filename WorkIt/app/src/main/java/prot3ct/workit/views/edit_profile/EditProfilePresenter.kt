package prot3ct.workit.views.edit_profile;

import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import prot3ct.workit.data.remote.UserData;
import prot3ct.workit.view_models.ProfileDetailsViewModel;
import prot3ct.workit.views.edit_profile.base.EditProfileContract;

public class EditProfilePresenter implements EditProfileContract.Presenter {
    private EditProfileContract.View view;
    private UserData userData;

    public EditProfilePresenter(EditProfileContract.View view, Context context) {
        this.view = view;
        userData = new UserData(context);
    }

    @Override
    public void getProfileDetails(int userId) {
        userData.getProfileDetails(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<ProfileDetailsViewModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ProfileDetailsViewModel profile) {
                        view.updateProfile(profile);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.notifyError("Error loading profile.");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void updateProfile(String fullName, String phone, String profilePictureAsString) {
        userData.updateProfile(fullName, phone, profilePictureAsString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                view.showDialogforLoading();
                            }

                            @Override
                            public void onNext(Boolean profile) {
                                view.notifySuccessful("Profile updated successfully.");
                                view.dismissDialog();
                                view.showProfileActivity();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.dismissDialog();
                                e.printStackTrace();
                                view.notifyError("Error updating profile.");
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }
}