package prot3ct.workit.views.edit_profile


import android.widget.TextView
import prot3ct.workit.utils.WorkItProgressDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.navigation.DrawerUtil
import android.content.Intent
import prot3ct.workit.view_models.ProfileDetailsViewModel
import android.graphics.BitmapFactory
import prot3ct.workit.views.profile.ProfileActivity
import android.widget.Toast
import android.app.Activity
import android.content.Context
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import prot3ct.workit.views.edit_profile.base.EditProfileContract

class EditProfileFragment : Fragment(), EditProfileContract.View {
    private lateinit var presenter: EditProfileContract.Presenter
    private lateinit var  toolbar: Toolbar
    private lateinit var  fullNameEditText: TextView
    private lateinit var  emailEditText: TextView
    private lateinit var  phoneEditText: TextView
    private lateinit var  profilePicture: ImageView
    private lateinit var  updateProfileButton: Button
    private var profilePictureAsString: String? = null
    private lateinit var dialog: WorkItProgressDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)

        dialog = WorkItProgressDialog(context)
    }

    override fun setPresenter(presenter: EditProfileContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        profilePicture = view.findViewById(R.id.id_edit_profile_picture)
        updateProfileButton = view.findViewById(R.id.id_update_profile_button)
        fullNameEditText = view.findViewById(R.id.id_edit_profile_name_edit_text)
        emailEditText = view.findViewById(R.id.id_edit_profile_email_edit_text)
        phoneEditText = view.findViewById(R.id.id_edit_profile_phone_edit_text)
        toolbar = view.findViewById(R.id.id_drawer_toolbar)
        val drawer = DrawerUtil(requireActivity(), toolbar)
        drawer.getDrawer()
        profilePicture.setOnClickListener(View.OnClickListener {
            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, 1)
        })
        updateProfileButton.setOnClickListener(View.OnClickListener {
            presenter.updateProfile(
                fullNameEditText.text.toString(),
                phoneEditText.text.toString(),
                profilePictureAsString!!
            )
        })
        presenter.getProfileDetails(requireActivity().intent.getIntExtra("userId", 0))
        return view
    }

    override fun updateProfile(profileDetails: ProfileDetailsViewModel?) {
        fullNameEditText.text = profileDetails!!.fullName
        emailEditText.text = profileDetails.email
        phoneEditText.text = profileDetails.phone
        profilePictureAsString = profileDetails.pictureAsString
        val decodedString = Base64.decode(profileDetails.pictureAsString, Base64.DEFAULT)
        val bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        profilePicture.setImageBitmap(bmp)
    }

    override fun showProfileActivity() {
        val intent = Intent(context, ProfileActivity::class.java)
        intent.putExtra("userId", requireActivity().intent.getIntExtra("userId", 0))
        intent.putExtra("myProfile", true)
        startActivity(intent)
    }

    override fun notifySuccessful(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun notifyError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return
            }
            val selectedImage = data.data
            profilePicture.setImageURI(selectedImage)

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

    override fun showDialogforLoading() {
        dialog.showProgress("Loading...")
    }

    override fun dismissDialog() {
        dialog.dismissProgress()
    }

    companion object {
        fun newInstance(): EditProfileFragment {
            return EditProfileFragment()
        }
    }
}