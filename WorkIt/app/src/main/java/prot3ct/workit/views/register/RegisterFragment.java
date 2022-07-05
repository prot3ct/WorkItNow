package prot3ct.workit.views.register;


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
import prot3ct.workit.views.login.LoginActivity;
import prot3ct.workit.views.register.base.RegisterContract;

public class RegisterFragment extends Fragment implements RegisterContract.View {
    private RegisterContract.Presenter presenter;
    private Context context;

    private EditText emailEditText;
    private EditText fullnameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;
    private Button returnToLoginButton;

    private WorkItProgressDialog dialog;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        this.dialog = new WorkItProgressDialog(context);
        this.registerButton = view.findViewById(R.id.id_register_button);
        this.emailEditText = view.findViewById(R.id.id_email_edit_text);
        this.fullnameEditText = view.findViewById(R.id.id_full_name_edit_text);
        this.passwordEditText = view.findViewById(R.id.id_password_edit_text);
        this.confirmPasswordEditText = view.findViewById(R.id.id_confirm_password_edit_text);
        this.returnToLoginButton = view.findViewById(R.id.id_return_to_login_button);

        this.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String fullName = fullnameEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (checkCredentials(email, password, confirmPassword, fullName)) {
                    presenter.registerUser(email, fullName, password);
                }
            }
        });

        this.returnToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginActivity();
            }
        });

        return view;
    }

    @Override
    public void showLoginActivity() {
        Intent intent = new Intent(this.context, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void notifySuccessful(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    private boolean checkCredentials(String email, String password, String confirmPassword, String fullName) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            notifyError("Invalid email address.");
            return false;
        }

        if (password.length() < 6) {
            notifyError("Password must be 6 or more symbols long.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            notifyError("Password and Confirm passowrd must the the same.");
            return false;
        }

        if (fullName.length() < 3) {
            notifyError("Full name must be atleast 3 symbols long.");
            return false;
        }

        return true;
    }

    @Override
    public void showDialogforLoading() {
        this.dialog.showProgress("Registering...");
    }

    @Override
    public void dismissDialog() {
        this.dialog.dismissProgress();
    }
}