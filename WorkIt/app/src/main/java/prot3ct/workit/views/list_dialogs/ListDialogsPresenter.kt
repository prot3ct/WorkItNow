package prot3ct.workit.views.list_dialogs;

import android.content.Context;

import com.stfalcon.chatkit.dialogs.DialogsList;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import prot3ct.workit.data.remote.AuthData;
import prot3ct.workit.data.remote.DialogData;
import prot3ct.workit.data.remote.TaskData;
import prot3ct.workit.models.Dialog;
import prot3ct.workit.view_models.DialogsListViewModel;
import prot3ct.workit.view_models.TaskDetailViewModel;
import prot3ct.workit.views.list_dialogs.base.ListDialogsContract;

public class ListDialogsPresenter implements ListDialogsContract.Presenter {
    private ListDialogsContract.View view;
    private DialogData dialogData;
    private AuthData authData;

    public ListDialogsPresenter(ListDialogsContract.View view, Context context) {
        this.view = view;
        this.dialogData = new DialogData(context);
        this.authData = new AuthData(context);
    }

    @Override
    public void getDialogs() {
        dialogData.getDialogs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<List<DialogsListViewModel>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(List<DialogsListViewModel> dialogs) {
                                view.updateDialogs(dialogs);
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
}
