package prot3ct.workit.views.chat;

import android.content.Context;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import prot3ct.workit.data.remote.AuthData;
import prot3ct.workit.data.remote.MessageData;
import prot3ct.workit.data.remote.TaskData;
import prot3ct.workit.view_models.ListMessagesViewModel;
import prot3ct.workit.views.chat.base.ChatContract;

public class ChatPresenter implements ChatContract.Presenter {
    private ChatContract.View view;
    private MessageData messageData;
    private AuthData authData;

    public ChatPresenter(ChatContract.View view, Context context) {
        this.view = view;
        this.messageData = new MessageData(context);
        this.authData = new AuthData(context);
    }

    @Override
    public void createMessage(String text, int authorId, int dialogId, String createdAt) {
        messageData.createMessage(text, authorId, dialogId, createdAt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean id) {
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                view.notifyError("Error submiting message.");
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }

    @Override
    public void getMessages(int dialogId) {
        messageData.getMessages(dialogId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<List<ListMessagesViewModel>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(List<ListMessagesViewModel> messages) {
                                view.updateChat(messages);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                view.notifyError("Error loading messages.");
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }

    @Override
    public int getLoggedInUserId() {
        return authData.getLoggedInUserId();
    }

    @Override
    public String getLoggedInUserName() {
        return authData.getLoggedInUserName();
    }
}
