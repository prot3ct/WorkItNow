package prot3ct.workit.views.profile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import prot3ct.workit.R;
import prot3ct.workit.views.profile.base.ProfileContract;

public class ProfileActivity extends AppCompatActivity {
    private ProfileContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfileFragment profileFragment =
                (ProfileFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (profileFragment == null) {
            profileFragment = ProfileFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, profileFragment)
                    .commit();
        }

        this.presenter = new ProfilePresenter(profileFragment, this);
        profileFragment.setPresenter(this.presenter);
    }
}