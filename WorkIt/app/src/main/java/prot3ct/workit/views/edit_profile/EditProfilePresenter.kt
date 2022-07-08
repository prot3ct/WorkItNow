package prot3ct.workit.views.edit_profile

import android.content.Context
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import prot3ct.workit.data.remote.UserData
import prot3ct.workit.view_models.ProfileDetailsViewModel
import prot3ct.workit.views.edit_profile.base.EditProfileContract

class EditProfilePresenter(private val view: EditProfileContract.View, context: Context) :
    EditProfileContract.Presenter {
    private val userData: UserData
    override fun getProfileDetails(userId: Int) {
        userData.getProfileDetails(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<ProfileDetailsViewModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(profile: ProfileDetailsViewModel) {
                        view.updateProfile(profile)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.notifyError("Error loading profile.")
                    }

                    override fun onComplete() {}
                })
    }

    override fun updateProfile(fullName: String, phone: String, profilePictureAsString: String) {
        userData.updateProfile(fullName, phone, profilePictureAsString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {
                        view.showDialogforLoading()
                    }

                    override fun onNext(profile: Boolean) {
                        view.notifySuccessful("Profile updated successfully.")
                        view.dismissDialog()
                        view.showProfileActivity()
                    }

                    override fun onError(e: Throwable) {
                        view.dismissDialog()
                        e.printStackTrace()
                        view.notifyError("Error updating profile.")
                    }

                    override fun onComplete() {}
                })
    }

    init {
        userData = UserData(context)
    }
}