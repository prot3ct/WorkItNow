package prot3ct.workit.view_models;

public class IsUserAssignableToTaskViewModel {
    private int pendingRequestId;
    private String isUserAssignableToTaskMessage;

    public String getIsUserAssignableToTaskMessage() {
        return isUserAssignableToTaskMessage;
    }

    public void setIsUserAssignableToTaskMessage(String isUserAssignableToTaskMessage) {
        this.isUserAssignableToTaskMessage = isUserAssignableToTaskMessage;
    }

    public int getPendingRequestId() {
        return pendingRequestId;
    }

    public void setPendingRequestId(int pendingRequestId) {
        this.pendingRequestId = pendingRequestId;
    }
}
