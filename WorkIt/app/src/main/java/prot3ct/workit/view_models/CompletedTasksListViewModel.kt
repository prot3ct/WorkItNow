package prot3ct.workit.view_models

data class CompletedTasksListViewModel(
    val taskId: Int,
    val title: String,
    val startDate: String,
    val supervisorFullName: String,
    val supervisorId: Int,
    val taskerId: Int,
    val taskerFullName: String,
    val hasSupervisorGivenRating: Boolean,
    val hasTaskerGivenRating: Boolean
)