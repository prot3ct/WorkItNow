package prot3ct.workit.views.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.register.RegisterFragment
import prot3ct.workit.views.register.RegisterPresenter
import prot3ct.workit.views.register.base.RegisterContract

class RegisterActivity : AppCompatActivity() {

    private lateinit var presenter: RegisterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var registerFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as RegisterFragment?
        if (registerFragment == null) {
            registerFragment = RegisterFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, registerFragment)
                .commit()
        }
        presenter = RegisterPresenter(registerFragment, this)
        registerFragment!!.setPresenter(presenter)
    }
}