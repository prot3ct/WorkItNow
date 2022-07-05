package prot3ct.workit.view_models;

public class CompletedTasksListViewModel {
    private int taskId;
    private String title;
    private String startDate;
    private String supervisorFullName;
    private int supervisorId;
    private int taskerId;
    private String taskerFullName;
    private boolean hasSupervisorGivenRating;
    private boolean hasTaskerGivenRating;

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

    public String getSupervisorFullName() {
        return supervisorFullName;
    }

    public void setSupervisorFullName(String supervisorFullName) {
        this.supervisorFullName = supervisorFullName;
    }

    public int getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }

    public int getTaskerId() {
        return taskerId;
    }

    public void setTaskerId(int taskerId) {
        this.taskerId = taskerId;
    }

    public String getTaskerFullName() {
        return taskerFullName;
    }

    public void setTaskerFullName(String taskerFullName) {
        this.taskerFullName = taskerFullName;
    }

    public boolean getHasSupervisorGivenRating() {
        return hasSupervisorGivenRating;
    }

    public void setHasSupervisorGivenRating(boolean hasSupervisorGivenRating) {
        this.hasSupervisorGivenRating = hasSupervisorGivenRating;
    }

    public boolean getHasTaskerGivenrating() {
        return hasTaskerGivenRating;
    }

    public void setHasTaskerGivenrating(boolean hasTaskerGivenrating) {
        this.hasTaskerGivenRating = hasTaskerGivenrating;
    }
}
