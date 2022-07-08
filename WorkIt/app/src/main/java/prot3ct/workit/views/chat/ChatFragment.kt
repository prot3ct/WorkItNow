package prot3ct.workit.views.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import prot3ct.workit.R;
import prot3ct.workit.models.Dialog;
import prot3ct.workit.models.Message;
import prot3ct.workit.models.User;
import prot3ct.workit.utils.WorkItProgressDialog;
import prot3ct.workit.view_models.ListMessagesViewModel;
import prot3ct.workit.views.chat.base.ChatContract;
import prot3ct.workit.views.navigation.DrawerUtil;

public class ChatFragment extends Fragment implements ChatContract.View {
    private ChatContract.Presenter presenter;
    private Context context;

    private MessagesListAdapter<Message> adapter;
    private MessageInput messageInput;
    private MessagesList messagesList;
    private WorkItProgressDialog dialog;
    private Toolbar toolbar;
    private List<Message> messagesToBeAdded;
    private int loggedInUserId;
    private String loggedInUserName;

    public ChatFragment() {
        // Required empty public constructor
    }

    private ArrayList<Dialog> dialogs = new ArrayList<Dialog>();

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void setPresenter(ChatContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        loggedInUserId = presenter.getLoggedInUserId();
        loggedInUserName = presenter.getLoggedInUserName();
        messageInput = view.findViewById(R.id.id_send_message_input);
        messagesList = view.findViewById(R.id.id_messages_list);
        adapter = new MessagesListAdapter<>(loggedInUserId+"", null);
        messagesToBeAdded = new ArrayList<Message>();
        messagesList.setAdapter(adapter);

        this.toolbar = view.findViewById(R.id.id_drawer_toolbar);
        this.dialog = new WorkItProgressDialog(context);

        messageInput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                Calendar date = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm");
                String dateAsString = dateFormat.format(date.getTime());

                User user = new User(loggedInUserId+"", loggedInUserName, null, false);

                Message msg = new Message(5+"", user, input.toString());

                presenter.createMessage(input.toString(), loggedInUserId, getActivity().getIntent().getIntExtra("dialogId", 0), dateAsString);
                adapter.addToStart(msg, true);
                return true;
            }
        });


        presenter.getMessages(getActivity().getIntent().getIntExtra("dialogId", 0));
        DrawerUtil drawer = new DrawerUtil(this.getActivity(), this.toolbar);
        drawer.getDrawer();

        return view;
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void notifySuccessful() {
        Toast.makeText(getContext(), "Task updated successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateChat(List<ListMessagesViewModel> messages) {
        for (ListMessagesViewModel msg: messages) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
            Date date = null;
            try {
                date = format.parse(msg.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            User user = new User(msg.getAuthorId()+"", msg.getAuthorName(), null, false);
            Message messageTobeAdded = new Message(1+"", user, msg.getText(), date);
            messagesToBeAdded.add(messageTobeAdded);
        }

        adapter.addToEnd(messagesToBeAdded, true);
    }

    @Override
    public void showDialogforLoading() {
        this.dialog.showProgress("Creating task...");
    }

    @Override
    public void dismissDialog() {
        this.dialog.dismissProgress();
    }
}
