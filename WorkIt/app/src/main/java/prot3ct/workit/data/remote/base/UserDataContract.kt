package prot3ct.workit.data.remote.base

import io.reactivex.Observable
import prot3ct.workit.view_models.ProfileDetailsViewModel

interface UserDataContract {

    fun getProfileDetails(userId: Int): Observable<ProfileDetailsViewModel>

    fun updateProfile(
        fullName: String,
        phone: String,
        profilePictureAsString: String?
    ): Observable<Boolean>
}