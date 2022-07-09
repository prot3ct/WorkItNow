package prot3ct.workit.views.profile.base

import prot3ct.workit.base.BaseView
import prot3ct.workit.view_models.ProfileDetailsViewModel

interface ProfileContract {
    interface View : BaseView<Presenter> {
        fun notifyError(errorMessage: String)
        fun updateProfile(profileDetails: ProfileDetailsViewModel)
        fun showEditProfileActivity()
        fun showDialogforLoading()
        fun showChatActivity(dialogId: Int)
        fun dismissDialog()
    }

    interface Presenter {
        fun getProfileDetails(userId: Int)
        fun createDialog(userId: Int)
    }
}