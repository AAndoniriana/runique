package mg.jaava.auth.domain

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasLowercase: Boolean = false,
    val hasUppercase: Boolean = false,
) {
    val isValidPassword: Boolean
        get() = hasMinLength && hasNumber && hasLowercase && hasUppercase
}