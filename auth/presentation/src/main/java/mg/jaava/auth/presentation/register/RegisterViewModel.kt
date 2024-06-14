package mg.jaava.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnLoginClick -> TODO()
            RegisterAction.OnRegisterClick -> TODO()
            RegisterAction.OnTogglePasswordVisibilityClick -> TODO()
        }
    }
}