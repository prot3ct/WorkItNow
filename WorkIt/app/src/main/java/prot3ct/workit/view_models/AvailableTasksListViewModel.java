package prot3ct.workit.view_models;

public class AvailableTasksListViewModel {
    private int taskId;
    private String title;
    private String startDate;
    private String fullName;
    private String supervisorRating;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSupervisorRating() {
        return supervisorRating;
    }

    public void setSupervisorRating(String supervisorRating) {
        this.supervisorRating = supervisorRating;
    }
}
