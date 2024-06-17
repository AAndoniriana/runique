package mg.jaava.auth.domain

class UserDataValidator(
    private val patternValidator: PatternValidator
) {

    fun isValidEmail(email: String): Boolean =
        patternValidator.matches(email.trim())

    fun validatePassword(password: String): PasswordValidationState {
        val hasMinimumLength = password.length >= MIN_PASSWORD_LENGTH
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }

        return PasswordValidationState(
            hasMinLength = hasMinimumLength,
            hasUppercase = hasUppercase,
            hasLowercase = hasLowercase,
            hasNumber = hasDigit
        )
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 9
    }
}