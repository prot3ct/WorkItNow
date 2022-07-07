package prot3ct.workit.data.remote.base

import io.reactivex.Observable
import prot3ct.workit.view_models.TaskRequestListViewModel

interface TaskRequestDataContract {
    fun createTaskRequest(taskId: Int): Observable<Boolean>

    fun updateTaskRequest(taskRequestId: Int, status: Int): Observable<Boolean>

    fun getAllTaskRequestsForTask(taskId: Int): Observable<List<TaskRequestListViewModel>>
}