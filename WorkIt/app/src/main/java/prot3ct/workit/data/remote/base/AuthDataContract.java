package prot3ct.workit.data.remote.base;

import io.reactivex.Observable;

public interface AuthDataContract {
    Observable<Boolean> login(String username, String password);

    Observable<Boolean> register(String email, String fullName, String password);

    Observable<Boolean> autoLogin();

    int getLoggedInUserId();

    String getLoggedInUserName();

    void logoutUser();

    boolean isLoggedIn();
}
