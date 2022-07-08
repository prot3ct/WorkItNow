package prot3ct.workit.view_models

data class MyTasksListViewModel(
    val taskId: Int,
    val title: String,
    val startDate: String,
    val hasPendingRequests: Boolean
)