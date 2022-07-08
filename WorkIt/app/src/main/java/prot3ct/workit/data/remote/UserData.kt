package prot3ct.workit.data.remote

import android.content.Context
import io.reactivex.Observable
import prot3ct.workit.data.remote.base.UserDataContract
import prot3ct.workit.utils.OkHttpRequester
import prot3ct.workit.config.ApiConstants
import prot3ct.workit.utils.GsonParser
import prot3ct.workit.data.local.UserSession
import prot3ct.workit.view_models.ProfileDetailsViewModel
import java.lang.Error
import kotlin.collections.HashMap

class UserData(context: Context) : UserDataContract {
    private val httpRequester: OkHttpRequester = OkHttpRequester()

    private val hashProvider: HashProvider = HashProvider()

    private val apiConstants: ApiConstants = ApiConstants()

    private val jsonParser: GsonParser = GsonParser()

    private val userSession: UserSession = UserSession(context)

    private val headers: MutableMap<String, String> = HashMap()

    override fun getProfileDetails(userId: Int): Observable<ProfileDetailsViewModel> {
        return httpRequester[apiConstants.getProfileDetailsUrl(userId), headers]
            .map { iHttpResponse ->
                if (iHttpResponse.code != 200) {
                    throw Error(iHttpResponse.message)
                }
                val responseBody = iHttpResponse.body
                jsonParser.fromJson(responseBody, ProfileDetailsViewModel::class.java)
            }
    }

    override fun updateProfile(
        fullName: String,
        phone: String,
        profilePictureAsString: String
    ): Observable<Boolean> {
        val profileDetails: MutableMap<String, String> = HashMap()
        profileDetails["userId"] = userSession.id.toString() + ""
        profileDetails["fullName"] = fullName
        profileDetails["phone"] = phone
        profileDetails["profilePictureAsString"] = profilePictureAsString
        return httpRequester
            .put(apiConstants.updateProfile(userSession.id), profileDetails, headers)
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                true
            }
    }

    init {
        headers["authToken"] = userSession.id.toString() + ":" + userSession.accessToken
    }
}