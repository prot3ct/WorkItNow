package prot3ct.workit.config.base;

public interface ApiConstantsContract {
    String loginUrl();

    String registerUrl();

    String autoLoginUserUrl();

    String createTaskUrl();

    String updateTaskUrl(int taskId);

    String getTaskDetailsUrl(int taskId);

    String deleteTaskUrl(int taskId);

    String getAvailableTasks(int userId, int page);

    String getAssignedTasksUrl(int userId);

    String getCompletedTasksUrl(int userId);

    String getMyTasks(int userId);

    String createTaskRequestUrl();

    String updateTaskRequestUrl(int requestId);

    String getRequestsForTaskUrl(int taskId);

    String deleteTaskRequestUrl(int taskRequestId);

    String createRaitingUrl();

    String updateProfile(int userId);

    String getProfileDetailsUrl(int userId);

    String getIsUserAssignableToTask();

    String updateAssignedUser(int taskId);

    String createDialog();

    String createMessage(int dialogId);

    String getMessages(int dialogId);

    String getDialogs(int userId);

    String getLocationLatLngUrl(String location);

    int responseSuccessCode();

    int responseErrorCode();

    int reponseServerErrorCode();
}