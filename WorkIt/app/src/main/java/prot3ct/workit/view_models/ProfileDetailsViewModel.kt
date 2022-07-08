package prot3ct.workit.view_models

class ProfileDetailsViewModel (
    val userId: Int,
    val email: String,
    val fullName: String,
    val phone: String,
    val ratingAsTasker: Int,
    val ratingAsSupervisor: Int,
    val numberOfReviewsAsTasker: Int,
    val getNumberOfReviewsAsSupervisor: Int,
    val pictureAsString: String,
)