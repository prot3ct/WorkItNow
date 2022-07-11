package prot3ct.workit.views.profile

import android.content.Context
import android.widget.TextView
import prot3ct.workit.utils.WorkItProgressDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.navigation.DrawerUtil
import prot3ct.workit.view_models.ProfileDetailsViewModel
import android.graphics.BitmapFactory
import android.content.Intent
import android.util.Base64
import android.view.View
import android.widget.ImageView
import prot3ct.workit.views.edit_profile.EditProfileActivity
import prot3ct.workit.views.chat.ChatActivity
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import prot3ct.workit.views.profile.base.ProfileContract

class ProfileFragment : Fragment(), ProfileContract.View {

    private lateinit var presenter: ProfileContract.Presenter
    private lateinit var toolbar: Toolbar
    private lateinit var fullNameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var ratingAsTaskerTextView: TextView
    private lateinit var ratingAsSupervisorTextView: TextView
    private lateinit var numberOfReviewsAsTaskerTextView: TextView
    private lateinit var numberOfReviewsAsSupervisorTextView: TextView
    private lateinit var profileTitleTextView: TextView
    private lateinit var editProfileButton: FloatingActionButton
    private lateinit var sendMessageButton: FloatingActionButton
    private lateinit var profilePicture: ImageView
    private lateinit var dialog: WorkItProgressDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)

        dialog = WorkItProgressDialog(context)
    }

    override fun setPresenter(presenter: ProfileContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        profilePicture = view.findViewById(R.id.id_profile_picture_image_view)
        sendMessageButton = view.findViewById(R.id.id_send_message_button)
        editProfileButton = view.findViewById(R.id.id_edit_profile_button)
        fullNameTextView = view.findViewById(R.id.id_profile_name_text_view)
        emailTextView = view.findViewById(R.id.id_profile_email_text_view)
        phoneTextView = view.findViewById(R.id.id_profile_phone_text_view)
        ratingAsTaskerTextView = view.findViewById(R.id.id_profile_raiting_as_tasker_text_view)
        ratingAsSupervisorTextView =
            view.findViewById(R.id.id_profile_rating_as_supervisor_text_view)
        numberOfReviewsAsTaskerTextView =
            view.findViewById(R.id.id_profile_number_of_reviews_as_tasker_text_view)
        numberOfReviewsAsSupervisorTextView =
            view.findViewById(R.id.id_profile_number_of_reviews_as_supervisor_text_view)
        profileTitleTextView = view.findViewById(R.id.id_profile_title_text_view)
        toolbar = view.findViewById(R.id.id_drawer_toolbar)
        val drawer = DrawerUtil(this.requireActivity(), toolbar)
        drawer.getDrawer()
        val isMyProfile = requireActivity().intent.getBooleanExtra("myProfile", false)
        if (isMyProfile) {
            sendMessageButton.visibility = View.GONE
            profileTitleTextView.text = "My profile"
            editProfileButton.setOnClickListener { showEditProfileActivity() }
        } else {
            sendMessageButton.setOnClickListener {
                presenter.createDialog(
                    requireActivity().intent.getIntExtra("userId", 0)
                )
            }
            editProfileButton.visibility = View.GONE
        }
        presenter.getProfileDetails(requireActivity().intent.getIntExtra("userId", 0))
        return view
    }

    override fun updateProfile(profileDetails: ProfileDetailsViewModel) {
        fullNameTextView.text = profileDetails.fullName
        emailTextView.text = profileDetails.email
        phoneTextView.text = profileDetails.phone
        (profileDetails.ratingAsTasker.toString() + " out of 5.0").also { ratingAsTaskerTextView.text = it }
        numberOfReviewsAsTaskerTextView.text =
            profileDetails.numberOfReviewsAsTasker.toString() + " reviews"
        ratingAsSupervisorTextView.text =
            profileDetails.ratingAsSupervisor.toString() + " out of 5.0"
        numberOfReviewsAsSupervisorTextView.text =
            profileDetails.getNumberOfReviewsAsSupervisor.toString() + " reviews"
        val decodedString = Base64.decode(profileDetails.pictureAsString, Base64.DEFAULT)
        val bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        profilePicture.setImageBitmap(bmp)
    }

    override fun showEditProfileActivity() {
        val intent = Intent(context, EditProfileActivity::class.java)
        intent.putExtra("userId", requireActivity().intent.getIntExtra("userId", 0))
        startActivity(intent)
    }

    override fun showChatActivity(dialogId: Int) {
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra("dialogId", dialogId)
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
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}