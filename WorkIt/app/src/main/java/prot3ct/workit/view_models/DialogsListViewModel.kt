package prot3ct.workit.view_models;

public class DialogsListViewModel {
    private int dialogId;
    private int user1Id;
    private String user1Name;
    private String user2Name;
    private int user2Id;
    private String user1Picture;
    private String user2Picture;
    private String lastMessageText;
    private String lastMessageCreatedAt;

    public int getDialogId() {
        return dialogId;
    }

    public void setDialogId(int dialogId) {
        this.dialogId = dialogId;
    }

    public int getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(int user1Id) {
        this.user1Id = user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(int user2Id) {
        this.user2Id = user2Id;
    }

    public String getLastMessageText() {
        return lastMessageText;
    }

    public void setLastMessageText(String lastMessageText) {
        this.lastMessageText = lastMessageText;
    }

    public String getLastMessageCreatedAt() {
        return lastMessageCreatedAt;
    }

    public void setLastMessageCreatedAt(String lastMessageCreatedAt) {
        this.lastMessageCreatedAt = lastMessageCreatedAt;
    }

    public String getUser1Name() {
        return user1Name;
    }

    public void setUser1Name(String user1Name) {
        this.user1Name = user1Name;
    }

    public String getUser2Name() {
        return user2Name;
    }

    public void setUser2Name(String user2Name) {
        this.user2Name = user2Name;
    }

    public String getUser1Picture() {
        return user1Picture;
    }

    public void setUser1Picture(String user1Picture) {
        this.user1Picture = user1Picture;
    }

    public String getUser2Picture() {
        return user2Picture;
    }

    public void setUser2Picture(String user2Picture) {
        this.user2Picture = user2Picture;
    }
}
