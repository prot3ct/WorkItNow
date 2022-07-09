package prot3ct.workit.views.register

import android.content.Context
import io.reactivex.Observer
import prot3ct.workit.data.remote.AuthData
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import prot3ct.workit.views.register.base.RegisterContract

class RegisterPresenter(private val view: RegisterContract.View, context: Context) :
    RegisterContract.Presenter {

    private val authData: AuthData

    override fun registerUser(email: String, fullName: String, password: String) {
        authData.register(email, fullName, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {
                        view.showDialogforLoading()
                    }

                    override fun onNext(value: Boolean) {
                        view.dismissDialog()
                        view.showLoginActivity()
                    }

                    override fun onError(e: Throwable) {
                        view.notifyError("Error ocurred when registering. Please try again later.")
                        view.dismissDialog()
                    }

                    override fun onComplete() {}
                })
    }

    init {
        authData = AuthData(context)
    }
}