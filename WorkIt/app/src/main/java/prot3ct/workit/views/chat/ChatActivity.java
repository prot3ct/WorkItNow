package prot3ct.workit.views.chat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import prot3ct.workit.R;
import prot3ct.workit.views.chat.base.ChatContract;

public class ChatActivity extends AppCompatActivity {
    private ChatContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_chat);

        ChatFragment editTaskFragment =
                (ChatFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (editTaskFragment == null) {
            editTaskFragment = ChatFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, editTaskFragment)
                    .commit();
        }

        this.presenter = new ChatPresenter(editTaskFragment, this);
        editTaskFragment.setPresenter(this.presenter);
    }
}
