package prot3ct.workit.views.login;

import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import prot3ct.workit.data.remote.AuthData;
import prot3ct.workit.views.login.base.LoginContract;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private AuthData authData;

    public LoginPresenter(LoginContract.View view, Context context) {
        this.view = view;
        authData = new AuthData(context);
    }

    @Override
    public void loginUser(String email, String password) {
        authData.login(email, password)
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
                        view.showListJobsActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.notifyError("Error ocurred when logining in. Please try again.");
                        view.dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public boolean isUserLoggedIn() {
        return this.authData.isLoggedIn();
    }

    @Override
    public void autoLoginUser() {
        authData.autoLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean value) {
                                if (value) {
                                    view.showListJobsActivity();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.notifyError("Error auto logining in.");
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }
}