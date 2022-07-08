package prot3ct.workit.data.remote.base

import io.reactivex.Observable
import prot3ct.workit.view_models.TaskDetailViewModel
import prot3ct.workit.view_models.IsUserAssignableToTaskViewModel
import prot3ct.workit.view_models.AssignedTasksListViewModel
import prot3ct.workit.view_models.AvailableTasksListViewModel
import prot3ct.workit.view_models.MyTasksListViewModel
import prot3ct.workit.view_models.CompletedTasksListViewModel

interface TaskDataContract {
    fun createTask(
        title: String, startDate: String, length: String,
        description: String, city: String, address: String, reward: String
    ): Observable<Boolean>

    fun updateTask(
        taskId: Int, title: String, startDate: String, length: String,
        description: String, city: String, address: String, reward: String
    ): Observable<Boolean>

    fun getTaskDetails(taskId: Int): Observable<TaskDetailViewModel>

    fun deleteTask(taskId: Int): Observable<Boolean>

    fun canAssignToTask(taskId: Int): Observable<IsUserAssignableToTaskViewModel>

    val assignedTasks: Observable<List<AssignedTasksListViewModel>>

    fun getAvailableTasks(
        page: Int,
        search: String
    ): Observable<List<AvailableTasksListViewModel>>

    val myTasks: Observable<List<MyTasksListViewModel>>

    val completedTasks: Observable<List<CompletedTasksListViewModel>>

    fun removeAssignedUser(taskId: Int): Observable<Boolean>
}