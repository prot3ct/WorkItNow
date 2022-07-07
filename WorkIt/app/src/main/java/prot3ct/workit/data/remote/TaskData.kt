package prot3ct.workit.data.remote;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import prot3ct.workit.config.ApiConstants;
import prot3ct.workit.data.local.UserSession;
import prot3ct.workit.data.remote.base.TaskDataContract;
import prot3ct.workit.view_models.AssignedTasksListViewModel;
import prot3ct.workit.view_models.AvailableTasksListViewModel;
import prot3ct.workit.view_models.CompletedTasksListViewModel;
import prot3ct.workit.view_models.IsUserAssignableToTaskViewModel;
import prot3ct.workit.view_models.MyTasksListViewModel;
import prot3ct.workit.view_models.TaskDetailViewModel;
import prot3ct.workit.models.base.HttpResponseContract;
import prot3ct.workit.utils.GsonParser;
import prot3ct.workit.utils.OkHttpRequester;

public class TaskData implements TaskDataContract {
    private final OkHttpRequester httpRequester;
    private final ApiConstants apiConstants;
    private final GsonParser jsonParser;
    private final UserSession userSession;
    private Map<String, String> headers;

    public TaskData(Context context) {
        this.jsonParser = new GsonParser();
        this.httpRequester = new OkHttpRequester();
        this.apiConstants = new ApiConstants();
        this.userSession = new UserSession(context);
        headers = new HashMap<>();
        headers.put("authToken", userSession.getId() + ":" + userSession.getAccessToken());
    }

    @Override
    public Observable<Boolean> createTask(String title, String startDate, String length,
                                          String description, String city, String address, String reward) {
        Map<String, String> taskDetails = new HashMap<>();
        taskDetails.put("title", title);
        taskDetails.put("startDate", startDate);
        taskDetails.put("length", length);
        taskDetails.put("description", description);
        taskDetails.put("city", city);
        taskDetails.put("address", address);
        taskDetails.put("reward", reward);
        taskDetails.put("creatorEmail", this.userSession.getEmail().replaceAll("\"", ""));

        return httpRequester
                .post(apiConstants.createTaskUrl(), taskDetails, headers)
                .map(new Function<HttpResponseContract, Boolean>() {
                    @Override
                    public Boolean apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode() || iHttpResponse.getCode() == apiConstants.reponseServerErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }

                        return true;
                    }
                });
    }

    @Override
    public Observable<Boolean> updateTask(int taskId, String title, String startDate, String length,
                                          String description, String city, String address, String reward) {
        Map<String, String> taskDetails = new HashMap<>();
        taskDetails.put("id", taskId + "");
        taskDetails.put("title", title);
        taskDetails.put("startDate", startDate);
        taskDetails.put("length", length);
        taskDetails.put("description", description);
        taskDetails.put("city", city);
        taskDetails.put("address", address);
        taskDetails.put("reward", reward);

        return httpRequester
                .put(apiConstants.updateTaskUrl(taskId), taskDetails, headers)
                .map(new Function<HttpResponseContract, Boolean>() {
                    @Override
                    public Boolean apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode() || iHttpResponse.getCode() == apiConstants.reponseServerErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }

                        return true;
                    }
                });
    }

    @Override
    public Observable<TaskDetailViewModel> getTaskDetails(int taskId) {
        return httpRequester
                .get(apiConstants.getTaskDetailsUrl(taskId), headers)
                .map(new Function<HttpResponseContract, TaskDetailViewModel>() {
                    @Override
                    public TaskDetailViewModel apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }

                        String responseBody = iHttpResponse.getBody();
                        return jsonParser.fromJson(responseBody, TaskDetailViewModel.class);
                    }
                });
    }

    @Override
    public Observable<Boolean> deleteTask(int taskId) {
        return httpRequester
                .delete(apiConstants.deleteTaskUrl(taskId), headers)
                .map(new Function<HttpResponseContract, Boolean>() {
                    @Override
                    public Boolean apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }

                        return true;
                    }
                });
    }

    @Override
    public Observable<List<AvailableTasksListViewModel>> getAvailableTasks(int page, String search) {
        return httpRequester
            .get(apiConstants.getAvailableTasks(userSession.getId(), page), search, headers)
            .map(new Function<HttpResponseContract, List<AvailableTasksListViewModel>>() {
                @Override
                public List<AvailableTasksListViewModel> apply(HttpResponseContract iHttpResponse) throws Exception {
                    if (iHttpResponse.getCode() != apiConstants.responseSuccessCode()) {
                        throw new Error(iHttpResponse.getMessage());
                    }

                    String responseBody = iHttpResponse.getBody();
                    List<AvailableTasksListViewModel> tasks = jsonParser.fromJson(responseBody, new TypeToken<List<AvailableTasksListViewModel>>(){}.getType());
                    return tasks;
                }
            });
    }

    @Override
    public Observable<List<AssignedTasksListViewModel>> getAssignedTasks() {
        return httpRequester
                .get(apiConstants.getAssignedTasksUrl(userSession.getId()), headers)
                .map(new Function<HttpResponseContract, List<AssignedTasksListViewModel>>() {
                    @Override
                    public List<AssignedTasksListViewModel> apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }

                        String responseBody = iHttpResponse.getBody();
                        return jsonParser.fromJson(responseBody, new TypeToken<List<AssignedTasksListViewModel>>(){}.getType());
                    }
                });
    }

    @Override
    public Observable<List<MyTasksListViewModel>> getMyTasks() {
        return httpRequester
            .get(apiConstants.getMyTasks(this.userSession.getId()), headers)
            .map(new Function<HttpResponseContract, List<MyTasksListViewModel>>() {
                @Override
                public List<MyTasksListViewModel> apply(HttpResponseContract iHttpResponse) throws Exception {
                if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                    throw new Error(iHttpResponse.getMessage());
                }

                String responseBody = iHttpResponse.getBody();
                return jsonParser.fromJson(responseBody, new TypeToken<List<MyTasksListViewModel>>(){}.getType());
                }
            });
    }

    @Override
    public Observable<List<CompletedTasksListViewModel>> getCompletedTasks() {
        return httpRequester
                .get(apiConstants.getCompletedTasksUrl(this.userSession.getId()), headers)
                .map(new Function<HttpResponseContract, List<CompletedTasksListViewModel>>() {
                    @Override
                    public List<CompletedTasksListViewModel> apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }

                        String responseBody = iHttpResponse.getBody();
                        return jsonParser.fromJson(responseBody, new TypeToken<List<CompletedTasksListViewModel>>(){}.getType());
                    }
                });
    }

    @Override
    public Observable<IsUserAssignableToTaskViewModel> canAssignToTask(int taskId) {
        Map<String, String> taskDetails = new HashMap<>();
        taskDetails.put("taskId", taskId + "");
        taskDetails.put("userId", userSession.getId() + "");

        return httpRequester
                .post(apiConstants.getIsUserAssignableToTask(), taskDetails, headers)
                .map(new Function<HttpResponseContract, IsUserAssignableToTaskViewModel>() {
                    @Override
                    public IsUserAssignableToTaskViewModel apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }

                        String responseBody = iHttpResponse.getBody();
                        return jsonParser.fromJson(responseBody, IsUserAssignableToTaskViewModel.class);
                    }
                });
    }

    @Override
    public Observable<Boolean> removeAssignedUser(int taskId) {
        Map<String, String> taskDetails = new HashMap<>();
        taskDetails.put("taskId", taskId + "");

        return httpRequester
                .put(apiConstants.updateAssignedUser(taskId), taskDetails, headers)
                .map(new Function<HttpResponseContract, Boolean>() {
                    @Override
                    public Boolean apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }

                        return true;
                    }
                });
    }
}
