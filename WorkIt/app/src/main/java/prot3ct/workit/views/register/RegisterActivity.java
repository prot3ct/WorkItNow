package prot3ct.workit.views.register;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import prot3ct.workit.R;
import prot3ct.workit.views.register.base.RegisterContract;

public class RegisterActivity extends AppCompatActivity {
    private RegisterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterFragment registerFragment =
                (RegisterFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (registerFragment == null) {
            registerFragment = RegisterFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, registerFragment)
                    .commit();
        }

        this.presenter = new RegisterPresenter(registerFragment, this);
        registerFragment.setPresenter(this.presenter);
    }
}
