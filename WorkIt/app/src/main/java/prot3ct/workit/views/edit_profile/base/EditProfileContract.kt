package prot3ct.workit.views.edit_profile.base

import prot3ct.workit.base.BaseView
import prot3ct.workit.view_models.ProfileDetailsViewModel

interface EditProfileContract {
    interface View : BaseView<Presenter> {
        fun notifyError(errorMessage: String)
        fun updateProfile(profileDetails: ProfileDetailsViewModel?)
        fun showDialogforLoading()
        fun notifySuccessful(msg: String)
        fun showProfileActivity()
        fun dismissDialog()
    }

    interface Presenter {
        fun updateProfile(fullName: String, phone: String, profilePictureAsString: String)
        fun getProfileDetails(userId: Int)
    }
}