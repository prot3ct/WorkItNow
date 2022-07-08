package prot3ct.workit.views.edit_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.edit_profile.EditProfileFragment
import prot3ct.workit.views.edit_profile.EditProfilePresenter
import prot3ct.workit.views.edit_profile.base.EditProfileContract

class EditProfileActivity : AppCompatActivity() {

    private lateinit var presenter: EditProfileContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        var editProfileFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as EditProfileFragment?
        if (editProfileFragment == null) {
            editProfileFragment = EditProfileFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, editProfileFragment)
                .commit()
        }
        presenter = EditProfilePresenter(editProfileFragment, this)
        editProfileFragment.setPresenter(presenter)
    }
}