package prot3ct.workit.views.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.profile.ProfileFragment
import prot3ct.workit.views.profile.ProfilePresenter
import prot3ct.workit.views.profile.base.ProfileContract

class ProfileActivity : AppCompatActivity() {

    private lateinit var presenter: ProfileContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var profileFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as ProfileFragment?
        if (profileFragment == null) {
            profileFragment = ProfileFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, profileFragment)
                .commit()
        }
        presenter = ProfilePresenter(profileFragment, this)
        profileFragment!!.setPresenter(presenter)
    }
}