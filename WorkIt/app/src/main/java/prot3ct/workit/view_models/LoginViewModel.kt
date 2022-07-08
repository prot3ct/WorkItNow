package prot3ct.workit.view_models

data class LoginViewModel(
    val userId: Int,
    val email: String,
    val fullName: String,
    val accessToken: String
)