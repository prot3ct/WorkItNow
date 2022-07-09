package prot3ct.workit.views.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.login.LoginFragment
import prot3ct.workit.views.login.LoginPresenter
import prot3ct.workit.views.login.base.LoginContract

class LoginActivity : AppCompatActivity() {
    private lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_tasks)
        var loginFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as LoginFragment?
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, loginFragment)
                .commit()
        }
        presenter = LoginPresenter(loginFragment, this)
        loginFragment!!.setPresenter(presenter)
    }
}