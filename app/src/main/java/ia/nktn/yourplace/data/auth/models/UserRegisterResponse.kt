package ia.nktn.yourplace.data.auth.models

data class UserRegisterResponse(
    val id: Double,
    val email: String,
    val isActive: Boolean,
    val isSuperuser: Boolean,
    val isVerified: Boolean,
    val phone: String?,
    val fullname: String?
)