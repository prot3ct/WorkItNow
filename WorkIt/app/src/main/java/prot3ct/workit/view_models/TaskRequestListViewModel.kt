package prot3ct.workit.view_models

data class TaskRequestListViewModel (
    val taskRequestId: Int,
    val fullName: String,
    val profilePictureAsString: String,
    val requesterId: Int
)