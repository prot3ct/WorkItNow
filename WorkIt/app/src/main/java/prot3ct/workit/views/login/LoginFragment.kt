package prot3ct.workit.views.login

import android.content.Context
import android.widget.EditText
import prot3ct.workit.utils.WorkItProgressDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import prot3ct.workit.R
import android.content.Intent
import android.view.View
import android.widget.Button
import prot3ct.workit.views.list_tasks.ListTasksActivity
import prot3ct.workit.views.register.RegisterActivity
import android.widget.Toast
import androidx.fragment.app.Fragment
import prot3ct.workit.views.login.base.LoginContract

class LoginFragment : Fragment(), LoginContract.View {
    private lateinit var presenter: LoginContract.Presenter
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var dialog: WorkItProgressDialog

    override fun setPresenter(presenter: LoginContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        emailEditText = view.findViewById(R.id.id_email_edit_text)
        passwordEditText = view.findViewById(R.id.id_password_edit_text)
        loginButton = view.findViewById(R.id.id_login_button)
        registerButton = view.findViewById(R.id.id_register_button)
        if (presenter.isUserLoggedIn) {
            presenter.autoLoginUser()
        }
        loginButton.setOnClickListener {
            presenter.loginUser(
                emailEditText.text.toString(), passwordEditText.text.toString()
            )
        }
        registerButton.setOnClickListener { showRegisterActivity() }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        dialog = WorkItProgressDialog(context)
    }

    override fun showListJobsActivity() {
        val intent = Intent(context, ListTasksActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun showRegisterActivity() {
        val intent = Intent(context, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun notifyError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun showDialogforLoading() {
        dialog.showProgress("Logging in...")
    }

    override fun dismissDialog() {
        dialog.dismissProgress()
    }

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}