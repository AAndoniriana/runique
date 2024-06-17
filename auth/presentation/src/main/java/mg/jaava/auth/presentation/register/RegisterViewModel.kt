package mg.jaava.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mg.jaava.auth.domain.UserDataValidator

@OptIn(ExperimentalFoundationApi::class)
class RegisterViewModel(
    private val userDataValidator: UserDataValidator
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    init {
        state.email.textAsFlow()
            .onEach { email ->
                val isValidEmail = userDataValidator.isValidEmail(email.toString())
                state = state.copy(
                    isEmailValid = isValidEmail,
                    canRegister = isValidEmail && state.passwordValidationState.isValidPassword
                            && state.isRegistering
                )
            }
            .launchIn(viewModelScope)

        state.password.textAsFlow()
            .onEach { password ->
                val isValidPassword = userDataValidator.validatePassword(password.toString())
                state = state.copy(
                    passwordValidationState = isValidPassword,
                    canRegister = isValidPassword.isValidPassword && state.isEmailValid
                            && state.isRegistering
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnLoginClick -> TODO()
            RegisterAction.OnRegisterClick -> TODO()
            RegisterAction.OnTogglePasswordVisibilityClick -> TODO()
        }
    }
}