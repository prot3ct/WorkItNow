package prot3ct.workit.views.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.chat.ChatFragment
import prot3ct.workit.views.chat.ChatPresenter
import prot3ct.workit.views.chat.base.ChatContract

class ChatActivity : AppCompatActivity() {

    private lateinit var presenter: ChatContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_chat)
        var editTaskFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as ChatFragment

        presenter = ChatPresenter(editTaskFragment, this)
        editTaskFragment.setPresenter(presenter)
    }
}