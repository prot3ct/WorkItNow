package prot3ct.workit.views.edit_profile;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import prot3ct.workit.R;
import prot3ct.workit.utils.WorkItProgressDialog;
import prot3ct.workit.view_models.ProfileDetailsViewModel;
import prot3ct.workit.views.edit_profile.base.EditProfileContract;
import prot3ct.workit.views.navigation.DrawerUtil;
import prot3ct.workit.views.profile.ProfileActivity;
import prot3ct.workit.views.register.RegisterActivity;

public class EditProfileFragment extends Fragment implements EditProfileContract.View {
    private EditProfileContract.Presenter presenter;
    private Context context;
    private Toolbar toolbar;

    private TextView fullNameEditText;
    private TextView emailEditText;
    private TextView phoneEditText;
    private ImageView profilePicture;
    private Button updateProfileButton;

    private String profilePictureAsString;

    private WorkItProgressDialog dialog;

    public EditProfileFragment() {
    }

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public void setPresenter(EditProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        this.profilePicture = view.findViewById(R.id.id_edit_profile_picture);
        this.updateProfileButton = view.findViewById(R.id.id_update_profile_button);
        this.fullNameEditText = view.findViewById(R.id.id_edit_profile_name_edit_text);
        this.emailEditText = view.findViewById(R.id.id_edit_profile_email_edit_text);
        this.phoneEditText = view.findViewById(R.id.id_edit_profile_phone_edit_text);
        this.toolbar = view.findViewById(R.id.id_drawer_toolbar);
        DrawerUtil drawer = new DrawerUtil(this.getActivity(), this.toolbar);
        drawer.getDrawer();
        this.dialog = new WorkItProgressDialog(context);

        this.profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
            }
        });

        this.updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.updateProfile(fullNameEditText.getText().toString(), phoneEditText.getText().toString(), profilePictureAsString);
            }
        });

        presenter.getProfileDetails(getActivity().getIntent().getIntExtra("userId", 0));

        return view;
    }

    @Override
    public void updateProfile(ProfileDetailsViewModel profileDetails) {
        this.fullNameEditText.setText(profileDetails.getFullName());
        this.emailEditText.setText(profileDetails.getEmail());
        this.phoneEditText.setText(profileDetails.getPhone());

        if (profileDetails.getPictureAsString() != null) {
            profilePictureAsString = profileDetails.getPictureAsString();
            byte[] decodedString = Base64.decode(profileDetails.getPictureAsString(), Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            this.profilePicture.setImageBitmap(bmp);
        }
    }

    @Override
    public void showProfileActivity() {
        Intent intent = new Intent(this.context, ProfileActivity.class);
        intent.putExtra("userId", getActivity().getIntent().getIntExtra("userId", 0));
        intent.putExtra("myProfile", true);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }

            Uri selectedImage = data.getData();
            profilePicture.setImageURI(selectedImage);

//            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            byte[] byteArray = stream.toByteArray();
//            profilePictureAsString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }

    @Override
    public void showDialogforLoading() {
        this.dialog.showProgress("Loading...");
    }

    @Override
    public void dismissDialog() {
        this.dialog.dismissProgress();
    }
}