package prot3ct.workit.views.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import prot3ct.workit.R;
import prot3ct.workit.utils.WorkItProgressDialog;
import prot3ct.workit.view_models.ProfileDetailsViewModel;
import prot3ct.workit.views.chat.ChatActivity;
import prot3ct.workit.views.edit_profile.EditProfileActivity;
import prot3ct.workit.views.profile.base.ProfileContract;
import prot3ct.workit.views.navigation.DrawerUtil;

public class ProfileFragment extends Fragment implements ProfileContract.View {
    private ProfileContract.Presenter presenter;
    private Context context;
    private Toolbar toolbar;

    private TextView fullNameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView ratingAsTaskerTextView;
    private TextView ratingAsSupervisorTextView;
    private TextView numberOfReviewsAsTaskerTextView;
    private TextView numberOfReviewsAsSupervisorTextView;
    private TextView profileTitleTextView;
    private FloatingActionButton editProfileButton;
    private FloatingActionButton sendMessageButton;
    private ImageView profilePicture;

    private WorkItProgressDialog dialog;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        this.profilePicture = view.findViewById(R.id.id_profile_picture_image_view);
        this.sendMessageButton = view.findViewById(R.id.id_send_message_button);
        this.editProfileButton = view.findViewById(R.id.id_edit_profile_button);
        this.fullNameTextView = view.findViewById(R.id.id_profile_name_text_view);
        this.emailTextView = view.findViewById(R.id.id_profile_email_text_view);
        this.phoneTextView = view.findViewById(R.id.id_profile_phone_text_view);
        this.ratingAsTaskerTextView = view.findViewById(R.id.id_profile_raiting_as_tasker_text_view);
        this.ratingAsSupervisorTextView = view.findViewById(R.id.id_profile_rating_as_supervisor_text_view);
        this.numberOfReviewsAsTaskerTextView = view.findViewById(R.id.id_profile_number_of_reviews_as_tasker_text_view);
        this.numberOfReviewsAsSupervisorTextView = view.findViewById(R.id.id_profile_number_of_reviews_as_supervisor_text_view);
        this.profileTitleTextView = view.findViewById(R.id.id_profile_title_text_view);
        this.toolbar = view.findViewById(R.id.id_drawer_toolbar);
        this.dialog = new WorkItProgressDialog(context);

        DrawerUtil drawer = new DrawerUtil(this.getActivity(), this.toolbar);
        drawer.getDrawer();

        boolean isMyProfile = getActivity().getIntent().getBooleanExtra("myProfile", false);

        if (isMyProfile) {
            sendMessageButton.setVisibility(View.GONE);
            profileTitleTextView.setText("My profile");
            this.editProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showEditProfileActivity();
                }
            });
        }
        else {
            sendMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.createDialog(getActivity().getIntent().getIntExtra("userId", 0));
                }
            });
            editProfileButton.setVisibility(View.GONE);
        }
        presenter.getProfileDetails(getActivity().getIntent().getIntExtra("userId", 0));

        return view;
    }

    @Override
    public void updateProfile(ProfileDetailsViewModel profileDetails) {
        this.fullNameTextView.setText(profileDetails.getFullName());
        this.emailTextView.setText(profileDetails.getEmail());
        this.phoneTextView.setText(profileDetails.getPhone());
        this.ratingAsTaskerTextView.setText(profileDetails.getRatingAsTasker() + " out of 5.0");
        this.numberOfReviewsAsTaskerTextView.setText(profileDetails.getNumberOfReviewsAsTasker() + " reviews");
        this.ratingAsSupervisorTextView.setText(profileDetails.getRatingAsSupervisor() + " out of 5.0");
        this.numberOfReviewsAsSupervisorTextView.setText(profileDetails.getGetNumberOfReviewsAsSupervisor() + " reviews");

        if (profileDetails.getPictureAsString() != null) {
            byte[] decodedString = Base64.decode(profileDetails.getPictureAsString(), Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            this.profilePicture.setImageBitmap(bmp);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void showEditProfileActivity() {
        Intent intent = new Intent(this.context, EditProfileActivity.class);
        intent.putExtra("userId", getActivity().getIntent().getIntExtra("userId", 0));
        startActivity(intent);
    }

    @Override
    public void showChatActivity(int dialogId) {
        Intent intent = new Intent(this.context, ChatActivity.class);
        intent.putExtra("dialogId", dialogId);
        startActivity(intent);
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDialogforLoading() {
        this.dialog.showProgress("Logging in...");
    }

    @Override
    public void dismissDialog() {
        this.dialog.dismissProgress();
    }
}