package mg.jaava.auth.presentation.register

import mg.jaava.core.presentation.ui.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess: RegisterEvent
    data class Error(val error: UiText): RegisterEvent
}