package mg.jaava.run.presentation.active_run

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ActiveRunViewModel: ViewModel() {
    var state by mutableStateOf(ActiveRunState())
        private set

    private val eventChannel = Channel<ActiveRunEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _hasLocationPermission = MutableStateFlow(false)

    fun onAction(action: ActiveRunAction) {
        when (action) {
            is ActiveRunAction.OnToggleRunClick -> {
                state = state.copy(
                    shouldTrack = !state.shouldTrack
                )
            }
            ActiveRunAction.OnFinishRunClick -> {}
            ActiveRunAction.OnResumeRunClick -> {}
            is ActiveRunAction.SubmitLocationPermissionInfo -> {
                _hasLocationPermission.value = action.acceptedLocationPermission
                state = state.copy(
                    showLocationRational = action.showLocationRational
                )
            }
            is ActiveRunAction.SubmitNotificationPermissionInfo -> {
                state = state.copy(
                    showNotificationRational = action.showNotificationRational
                )
            }
            is ActiveRunAction.DismissRationalDialog -> {
                state = state.copy(
                    showLocationRational = false,
                    showNotificationRational = false
                )
            }
            else -> Unit
        }
    }
}