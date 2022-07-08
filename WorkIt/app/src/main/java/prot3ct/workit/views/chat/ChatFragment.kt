package prot3ct.workit.views.chat

import com.stfalcon.chatkit.messages.MessagesListAdapter
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesList
import prot3ct.workit.utils.WorkItProgressDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import prot3ct.workit.R
import com.stfalcon.chatkit.messages.MessageInput.InputListener
import prot3ct.workit.views.navigation.DrawerUtil
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import prot3ct.workit.models.Dialog
import prot3ct.workit.models.Message
import prot3ct.workit.models.User
import prot3ct.workit.view_models.ListMessagesViewModel
import prot3ct.workit.views.chat.ChatFragment
import prot3ct.workit.views.chat.base.ChatContract
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatFragment : Fragment(), ChatContract.View {
    private lateinit var presenter: ChatContract.Presenter
    private lateinit var adapter: MessagesListAdapter<Message>
    private lateinit var messageInput: MessageInput
    private lateinit var messagesList: MessagesList
    private val dialog: WorkItProgressDialog = WorkItProgressDialog(context)
    private lateinit var toolbar: Toolbar
    private val messagesToBeAdded: MutableList<Message> = ArrayList()
    private lateinit var loggedInUserName: String

    override fun setPresenter(presenter: ChatContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        val loggedInUserId = presenter.loggedInUserId
        loggedInUserName = presenter.loggedInUserName
        messageInput = view.findViewById(R.id.id_send_message_input)
        messagesList = view.findViewById(R.id.id_messages_list)
        adapter = MessagesListAdapter(loggedInUserId.toString() + "", null)
        messagesList.setAdapter(adapter)
        toolbar = view.findViewById(R.id.id_drawer_toolbar)
        messageInput.setInputListener(InputListener { input ->
            val date = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd,HH:mm")
            val dateAsString = dateFormat.format(date.time)
            val user = User(loggedInUserId.toString() + "", loggedInUserName!!, null, false)
            val msg = Message(5.toString() + "", user, input.toString())
            presenter.createMessage(
                input.toString(),
                loggedInUserId,
                requireActivity().intent.getIntExtra("dialogId", 0),
                dateAsString
            )
            adapter.addToStart(msg, true)
            true
        })
        presenter.getMessages(requireActivity().intent.getIntExtra("dialogId", 0))
        val drawer = DrawerUtil(this.activity, toolbar)
        drawer.getDrawer()
        return view
    }

    override fun notifyError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun notifySuccessful() {
        Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show()
    }

    override fun updateChat(messages: List<ListMessagesViewModel>) {
        for (msg in messages) {
            val format: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH)
            var date: Date? = null
            try {
                date = format.parse(msg.createdAt)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val user = User(msg.authorId.toString() + "", msg.authorName, null, false)
            val messageTobeAdded = Message(1.toString() + "", user, msg.text, date!!)
            messagesToBeAdded.add(messageTobeAdded)
        }
        adapter.addToEnd(messagesToBeAdded, true)
    }

    override fun showDialogForLoading() {
        dialog.showProgress("Creating task...")
    }

    override fun dismissDialog() {
        dialog.dismissProgress()
    }

    companion object {
        fun newInstance(): ChatFragment {
            return ChatFragment()
        }
    }
}