package prot3ct.workit.views.register;

import android.content.Context;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import prot3ct.workit.data.remote.AuthData;
import prot3ct.workit.views.register.base.RegisterContract;

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View view;
    private AuthData authData;

    public RegisterPresenter(RegisterContract.View view, Context context) {
        this.view = view;
        authData = new AuthData(context);
    }

    @Override
    public void registerUser(String email, String fullName, String password) {
        authData.register(email, fullName, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        view.showDialogforLoading();
                    }

                    @Override
                    public void onNext(Boolean value) {
                        view.dismissDialog();
                        view.showLoginActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.notifyError("Error ocurred when registering. Please try again later.");
                        view.dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}