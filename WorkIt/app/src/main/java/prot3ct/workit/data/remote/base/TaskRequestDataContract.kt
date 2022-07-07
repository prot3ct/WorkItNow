package prot3ct.workit.data.remote.base;

import java.util.List;

import io.reactivex.Observable;
import prot3ct.workit.view_models.TaskRequestListViewModel;

public interface TaskRequestDataContract {
    Observable<Boolean> createTaskRequest(int taskId);

    Observable<Boolean> updateTaskRequest(int taskRequestId, int status);

    Observable<List<TaskRequestListViewModel>> getAllTaskRequestsForTask(int taskId);
}
