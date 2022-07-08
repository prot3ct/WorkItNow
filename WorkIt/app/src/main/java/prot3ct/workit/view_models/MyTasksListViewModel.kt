package prot3ct.workit.view_models;

public class MyTasksListViewModel {
    private int taskId;
    private String title;
    private String startDate;
    private boolean hasPendingRequests;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public boolean hasPendingRequest() {
        return hasPendingRequests;
    }

    public void setHasPendingRequest(boolean hasPendingRequest) {
        this.hasPendingRequests = hasPendingRequest;
    }
}
