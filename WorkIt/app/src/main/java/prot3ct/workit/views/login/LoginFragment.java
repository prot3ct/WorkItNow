package prot3ct.workit.views.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import prot3ct.workit.R;
import prot3ct.workit.utils.WorkItProgressDialog;
import prot3ct.workit.views.list_tasks.ListTasksActivity;
import prot3ct.workit.views.login.base.LoginContract;
import prot3ct.workit.views.register.RegisterActivity;

public class LoginFragment extends Fragment implements LoginContract.View {
    private LoginContract.Presenter presenter;
    private Context context;

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    private WorkItProgressDialog dialog;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        this.dialog = new WorkItProgressDialog(context);
        this.emailEditText = view.findViewById(R.id.id_email_edit_text);
        this.passwordEditText = view.findViewById(R.id.id_password_edit_text);
        this.loginButton = view.findViewById(R.id.id_login_button);
        this.registerButton = view.findViewById(R.id.id_register_button);

        if (presenter.isUserLoggedIn()) {
            presenter.autoLoginUser();
        }

        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loginUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

        this.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterActivity();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void showListJobsActivity() {
        Intent intent = new Intent(this.context, ListTasksActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showRegisterActivity() {
        Intent intent = new Intent(this.context, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDialogforLoading() {
        this.dialog.showProgress("Logging in...");
    }

    @Override
    public void dismissDialog() {
        this.dialog.dismissProgress();
    }
}