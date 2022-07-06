package prot3ct.workit.views.edit_profile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import prot3ct.workit.R;
import prot3ct.workit.views.edit_profile.base.EditProfileContract;

public class EditProfileActivity extends AppCompatActivity {
    private EditProfileContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        EditProfileFragment editProfileFragment =
                (EditProfileFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (editProfileFragment == null) {
            editProfileFragment = EditProfileFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, editProfileFragment)
                    .commit();
        }

        this.presenter = new EditProfilePresenter(editProfileFragment, this);
        editProfileFragment.setPresenter(this.presenter);
    }
}