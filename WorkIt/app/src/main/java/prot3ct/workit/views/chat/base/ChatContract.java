package prot3ct.workit.views.chat.base;

import java.util.List;

import prot3ct.workit.base.BaseView;
import prot3ct.workit.view_models.ListMessagesViewModel;

public interface ChatContract {
    interface View extends BaseView<ChatContract.Presenter> {
        void notifySuccessful();

        void showDialogforLoading();

        void dismissDialog();

        void updateChat(List<ListMessagesViewModel> messages);

        void notifyError(String errorMessage);
    }

    interface Presenter {
        void createMessage(String text, int authorId, int dialogId, String createdAt);

        int getLoggedInUserId();

        String getLoggedInUserName();

        void getMessages(int dialogId);
    }
}
