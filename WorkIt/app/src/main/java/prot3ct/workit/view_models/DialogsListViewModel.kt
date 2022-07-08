package prot3ct.workit.view_models

data class DialogsListViewModel(
    val dialogId: Int,
    val user1Id: Int,
    val user1Name: String,
    val user2Name: String,
    val user2Id: Int,
    val user1Picture: String,
    val user2Picture: String,
    val lastMessageText: String,
    val lastMessageCreatedAt: String
)