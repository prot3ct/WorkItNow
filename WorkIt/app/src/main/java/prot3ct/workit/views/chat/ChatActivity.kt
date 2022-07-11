package prot3ct.workit.views.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import prot3ct.workit.R
import prot3ct.workit.views.chat.ChatFragment
import prot3ct.workit.views.chat.ChatPresenter
import prot3ct.workit.views.chat.base.ChatContract
import prot3ct.workit.views.completed_tasks.CompletedTasksFragment

class ChatActivity : AppCompatActivity() {

    private lateinit var presenter: ChatContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_chat)
        var editTaskFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as ChatFragment?

        if (editTaskFragment == null) {
            editTaskFragment = ChatFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, editTaskFragment)
                .commit()
        }

        presenter = ChatPresenter(editTaskFragment, this)
        editTaskFragment.setPresenter(presenter)
    }
}