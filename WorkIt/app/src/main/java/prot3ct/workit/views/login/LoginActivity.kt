package prot3ct.workit.views.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import prot3ct.workit.R;
import prot3ct.workit.views.login.base.LoginContract;

public class LoginActivity extends AppCompatActivity {
    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);

        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, loginFragment)
                    .commit();
        }

        this.presenter = new LoginPresenter(loginFragment, this);
        loginFragment.setPresenter(this.presenter);
    }
}