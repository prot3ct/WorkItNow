package prot3ct.workit.data.local.base;

public interface UserSessionContract {
    String getEmail();

    void setEmail(String email);

    String getAccessToken();

    String getFullName();

    void setFullName(String fullName);

    void setAccessToken(String accessToken);

    void setId(int id);

    int getId();

    boolean isUserLoggedIn();

    void clearSession();
}