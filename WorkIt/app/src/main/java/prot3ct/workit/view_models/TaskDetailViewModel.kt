package prot3ct.workit.view_models;

public class TaskDetailViewModel {
    private int taskId;
    private String title;
    private String startDate;
    private int length;
    private String description;
    private String city;
    private String address;
    private double reward;
    private double minRaiting;
    private String supervisorName;
    private double supervisorRating;
    private int supervisorId;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int id) {
        this.taskId = id;
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public double getMinRaiting() {
        return minRaiting;
    }

    public void setMinRaiting(double minRaiting) {
        this.minRaiting = minRaiting;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public double getSupervisorRating() {
        return supervisorRating;
    }

    public void setSupervisorRating(double supervisorRating) {
        this.supervisorRating = supervisorRating;
    }

    public int getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }
}
