package prot3ct.workit.views.list_dialogs

import android.content.Context
import prot3ct.workit.utils.WorkItProgressDialog
import com.stfalcon.chatkit.dialogs.DialogsList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.navigation.DrawerUtil
import android.widget.Toast
import prot3ct.workit.view_models.DialogsListViewModel
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import android.graphics.BitmapFactory
import android.content.Intent
import android.util.Base64
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import prot3ct.workit.models.Dialog
import prot3ct.workit.models.Message
import prot3ct.workit.models.User
import prot3ct.workit.views.chat.ChatActivity
import prot3ct.workit.views.list_dialogs.base.ListDialogsContract
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ListDialogsFragment : Fragment(), ListDialogsContract.View {

    private lateinit var presenter: ListDialogsContract.Presenter
    private lateinit var dialog: WorkItProgressDialog
    private lateinit var toolbar: Toolbar
    private lateinit var dialogsListView: DialogsList
    private lateinit var dialogsToBeAdded: ArrayList<Dialog>

    override fun onAttach(context: Context) {
        super.onAttach(context)

        dialog = WorkItProgressDialog(context)
    }

    override fun setPresenter(presenter: ListDialogsContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_dialogs, container, false)
        toolbar = view.findViewById(R.id.id_drawer_toolbar)
        dialog = WorkItProgressDialog(requireContext())
        dialogsToBeAdded = ArrayList()
        dialogsListView = view.findViewById(R.id.dialogsList)
        val drawer = DrawerUtil(requireActivity(), toolbar)
        drawer.getDrawer()
        presenter.dialogs
        return view
    }

    override fun notifyError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun notifySuccessful() {
        Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show()
    }

    override fun showDialogforLoading() {
        dialog.showProgress("Creating task...")
    }

    override fun dismissDialog() {
        dialog.dismissProgress()
    }

    override fun updateDialogs(dialogs: List<DialogsListViewModel>) {
        for ((dialogId, user1Id, user1Name, user2Name, user2Id, user1Picture, user2Picture, lastMessageText, lastMessageCreatedAt) in dialogs) {
            val lastMessageUser = User(1.toString() + "", "", null, false)
            var lastMessage: Message
            val format: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH)
            var lastMessageDate: Date? = null
            try {
                lastMessageDate = format.parse(lastMessageCreatedAt)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            lastMessage =
                Message(1.toString() + "", lastMessageUser, lastMessageText, lastMessageDate!!)
            var dialogName: String
            var picture: String
            var userChattingWith: User
            if (presenter.loggedInUserId == user1Id) {
                dialogName = user2Name
                picture = user2Picture
                userChattingWith = User(user2Id.toString() + "", user2Name, null, false)
            } else {
                dialogName = user1Name
                picture = user1Picture
                userChattingWith = User(user1Id.toString() + "", user1Name, null, false)
            }
            val users = ArrayList<User>()
            users.add(userChattingWith)
            val dialogToBeAdded =
                Dialog(dialogId.toString() + "", dialogName, picture, users, lastMessage, 0)
            dialogsToBeAdded.add(dialogToBeAdded)
        }
        val dialogsListAdapter: DialogsListAdapter<Dialog> =
            DialogsListAdapter<Dialog> { imageView, url, payload ->
                if (url == null) {
                    imageView.setImageDrawable(requireActivity().resources.getDrawable(R.drawable.blank_profile_picture))
                } else {
                    val decodedString = Base64.decode(url, Base64.DEFAULT)
                    val bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    imageView.setImageBitmap(bmp)
                }
            }
        dialogsListAdapter.setItems(dialogsToBeAdded)
        dialogsListAdapter.setOnDialogClickListener { dialog ->
            showChatActivity(
                dialog.id.toInt()
            )
        }
        dialogsListView.setAdapter(dialogsListAdapter)
    }

    private fun showChatActivity(dialogId: Int) {
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra("dialogId", dialogId)
        startActivity(intent)
    }

    companion object {
        fun newInstance(): ListDialogsFragment {
            return ListDialogsFragment()
        }
    }
}