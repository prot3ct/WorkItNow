package prot3ct.workit.views.list_dialogs;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import prot3ct.workit.R;
import prot3ct.workit.models.Dialog;
import prot3ct.workit.models.Message;
import prot3ct.workit.models.User;
import prot3ct.workit.utils.WorkItProgressDialog;
import prot3ct.workit.view_models.DialogsListViewModel;
import prot3ct.workit.view_models.TaskDetailViewModel;
import prot3ct.workit.views.chat.ChatActivity;
import prot3ct.workit.views.list_dialogs.base.ListDialogsContract;
import prot3ct.workit.views.list_tasks.ListTasksActivity;
import prot3ct.workit.views.navigation.DrawerUtil;

public class ListDialogsFragment extends Fragment implements ListDialogsContract.View {
    private ListDialogsContract.Presenter presenter;
    private Context context;

    private WorkItProgressDialog dialog;
    private Toolbar toolbar;
    private DialogsList dialogsListView;
    private ArrayList<Dialog> dialogsToBeAdded;

    public ListDialogsFragment() {
        // Required empty public constructor
    }

    private ArrayList<Dialog> dialogs = new ArrayList<Dialog>();

    public static ListDialogsFragment newInstance() {
        return new ListDialogsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void setPresenter(ListDialogsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_dialogs, container, false);

        this.toolbar = view.findViewById(R.id.id_drawer_toolbar);
        this.dialog = new WorkItProgressDialog(context);
        this.dialogsToBeAdded = new ArrayList<>();
        this.dialogsListView = view.findViewById(R.id.dialogsList);

        DrawerUtil drawer = new DrawerUtil(this.getActivity(), this.toolbar);
        drawer.getDrawer();

        presenter.getDialogs();

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
    public void showDialogforLoading() {
        this.dialog.showProgress("Creating task...");
    }

    @Override
    public void dismissDialog() {
        this.dialog.dismissProgress();
    }

    @Override
    public void updateDialogs(List<DialogsListViewModel> dialogs) {
        for (DialogsListViewModel dialog: dialogs) {
            User lastMessageUser = new User(1+"", "", null, false);

            Message lastMessage;
            if (dialog.getLastMessageText() == null) {
            lastMessage = new Message(1+"", lastMessageUser, "", new Date());
            }
            else {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
                Date lastMessageDate = null;
                try {
                    lastMessageDate = format.parse(dialog.getLastMessageCreatedAt());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                lastMessage = new Message(1+"", lastMessageUser, dialog.getLastMessageText(), lastMessageDate);
            }

            String dialogName;
            String picture;
            User userChattingWith;
            if (presenter.getLoggedInUserId() == dialog.getUser1Id()) {
                dialogName = dialog.getUser2Name();
                picture = dialog.getUser2Picture();
                userChattingWith = new User(dialog.getUser2Id()+"", dialog.getUser2Name(), null, false);
            }
            else {
                dialogName = dialog.getUser1Name();
                picture = dialog.getUser1Picture();
                userChattingWith = new User(dialog.getUser1Id()+"", dialog.getUser1Name(), null, false);
            }

            ArrayList<User> users = new ArrayList<>();
            users.add(userChattingWith);

            Dialog dialogToBeAdded = new Dialog(dialog.getDialogId()+"", dialogName, picture, users, lastMessage, 0);
            dialogsToBeAdded.add(dialogToBeAdded);
        }

        DialogsListAdapter dialogsListAdapter = new DialogsListAdapter<>(new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url, Object payload) {
                if(url == null) {
                    imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.blank_profile_picture));
                }
                else {
                    byte[] decodedString = Base64.decode(url, Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageView.setImageBitmap(bmp);
                }
            }
        });
        dialogsListAdapter.setItems(dialogsToBeAdded);

        dialogsListAdapter.setOnDialogClickListener(new DialogsListAdapter.OnDialogClickListener<Dialog>() {
            @Override
            public void onDialogClick(Dialog dialog) {
                showChatActivity(Integer.parseInt(dialog.getId()));
            }
        });


        dialogsListView.setAdapter(dialogsListAdapter);
    }

    public void showChatActivity(int dialogId) {
        Intent intent = new Intent(this.context, ChatActivity.class);
        intent.putExtra("dialogId", dialogId);
        startActivity(intent);
    }
}
