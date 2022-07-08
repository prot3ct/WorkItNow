package prot3ct.workit.view_models

data class AvailableTasksListViewModel(
    val taskId: Int,
    val title: String,
    val startDate: String,
    val fullName: String,
    val supervisorRating: String
)