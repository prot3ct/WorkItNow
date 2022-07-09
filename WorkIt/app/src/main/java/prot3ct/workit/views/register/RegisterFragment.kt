package prot3ct.workit.views.register

import android.widget.EditText
import prot3ct.workit.utils.WorkItProgressDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import prot3ct.workit.R
import android.content.Intent
import android.util.Patterns
import android.view.View
import android.widget.Button
import prot3ct.workit.views.login.LoginActivity
import android.widget.Toast
import androidx.fragment.app.Fragment
import prot3ct.workit.views.register.base.RegisterContract

class RegisterFragment : Fragment(), RegisterContract.View {

    private lateinit var presenter: RegisterContract.Presenter
    private lateinit var emailEditText: EditText
    private lateinit var fullnameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var returnToLoginButton: Button
    private val dialog: WorkItProgressDialog = WorkItProgressDialog(context)

    override fun setPresenter(presenter: RegisterContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        registerButton = view.findViewById(R.id.id_register_button)
        emailEditText = view.findViewById(R.id.id_email_edit_text)
        fullnameEditText = view.findViewById(R.id.id_full_name_edit_text)
        passwordEditText = view.findViewById(R.id.id_password_edit_text)
        confirmPasswordEditText = view.findViewById(R.id.id_confirm_password_edit_text)
        returnToLoginButton = view.findViewById(R.id.id_return_to_login_button)
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val fullName = fullnameEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            if (checkCredentials(email, password, confirmPassword, fullName)) {
                presenter.registerUser(email, fullName, password)
            }
        }
        returnToLoginButton.setOnClickListener { showLoginActivity() }
        return view
    }

    override fun showLoginActivity() {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun notifySuccessful(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun notifyError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun checkCredentials(
        email: String,
        password: String,
        confirmPassword: String,
        fullName: String
    ): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            notifyError("Invalid email address.")
            return false
        }
        if (password.length < 6) {
            notifyError("Password must be 6 or more symbols long.")
            return false
        }
        if (password != confirmPassword) {
            notifyError("Password and Confirm passowrd must the the same.")
            return false
        }
        if (fullName.length < 3) {
            notifyError("Full name must be atleast 3 symbols long.")
            return false
        }
        return true
    }

    override fun showDialogforLoading() {
        dialog.showProgress("Registering...")
    }

    override fun dismissDialog() {
        dialog.dismissProgress()
    }

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }
}