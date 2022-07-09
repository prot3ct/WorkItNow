package prot3ct.workit.views.profile

import android.content.Context
import io.reactivex.Observer
import prot3ct.workit.data.remote.DialogData
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import prot3ct.workit.data.remote.UserData
import prot3ct.workit.view_models.ProfileDetailsViewModel
import prot3ct.workit.views.profile.base.ProfileContract

class ProfilePresenter(private val view: ProfileContract.View, context: Context) :
    ProfileContract.Presenter {

    private val userData: UserData
    private val dialogData: DialogData

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

    override fun createDialog(userId: Int) {
        dialogData.createDialog(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(id: String) {
                        view.showChatActivity(id.toInt())
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.notifyError("Error opening dialog")
                    }

                    override fun onComplete() {}
                })
    }

    init {
        userData = UserData(context)
        dialogData = DialogData(context)
    }
}