package prot3ct.workit.views.login

import android.content.Context
import io.reactivex.Observer
import prot3ct.workit.data.remote.AuthData
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import prot3ct.workit.views.login.base.LoginContract

class LoginPresenter(private val view: LoginContract.View, context: Context) :
    LoginContract.Presenter {
    private val authData: AuthData

    override val isUserLoggedIn: Boolean
        get() = authData.isLoggedIn

    override fun loginUser(email: String, password: String) {
        authData.login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {
                        view.showDialogforLoading()
                    }

                    override fun onNext(value: Boolean) {
                        view.dismissDialog()
                        view.showListJobsActivity()
                    }

                    override fun onError(e: Throwable) {
                        view.notifyError("Error ocurred when logining in. Please try again.")
                        view.dismissDialog()
                    }

                    override fun onComplete() {}
                })
    }

    override fun autoLoginUser() {
        authData.autoLogin()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(value: Boolean) {
                        if (value) {
                            view.showListJobsActivity()
                        }
                    }

                    override fun onError(e: Throwable) {
                        view.notifyError("Error auto logining in.")
                    }

                    override fun onComplete() {}
                })
    }

    init {
        authData = AuthData(context)
    }
}