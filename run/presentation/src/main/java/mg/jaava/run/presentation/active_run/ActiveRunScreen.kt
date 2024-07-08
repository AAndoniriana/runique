@file:OptIn(ExperimentalMaterial3Api::class)

package mg.jaava.run.presentation.active_run

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mg.jaava.core.presentationdesignsystem.RuniqueTheme
import mg.jaava.core.presentationdesignsystem.StartIcon
import mg.jaava.core.presentationdesignsystem.StopIcon
import mg.jaava.core.presentationdesignsystem.component.RuniqueDialog
import mg.jaava.core.presentationdesignsystem.component.RuniqueFloatingActionButton
import mg.jaava.core.presentationdesignsystem.component.RuniqueOutlinedActionButton
import mg.jaava.core.presentationdesignsystem.component.RuniqueScaffold
import mg.jaava.core.presentationdesignsystem.component.RuniqueToolbar
import mg.jaava.run.presentation.R
import mg.jaava.run.presentation.active_run.components.RunDataCard
import mg.jaava.run.presentation.util.hasLocationPermission
import mg.jaava.run.presentation.util.hasNotificationPermission
import mg.jaava.run.presentation.util.shouldShowLocationPermissionRational
import mg.jaava.run.presentation.util.shouldShowNotificationPermissionRational
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveRunScreenRoot(
    viewModel: ActiveRunViewModel = koinViewModel()
) {
    ActiveRunScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ActiveRunScreen(
    state: ActiveRunState,
    onAction: (ActiveRunAction) -> Unit,
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val hasCoarseLocationPermission = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFineLocationPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val hasNotificationPermission = if (Build.VERSION.SDK_INT >= 33) {
            perms[Manifest.permission.POST_NOTIFICATIONS] == true
        } else true

        val activity = context as ComponentActivity
        val showLocationPermissionRational = activity.shouldShowLocationPermissionRational()
        val showNotificationPermissionRational = activity.shouldShowNotificationPermissionRational()

        onAction(ActiveRunAction.SubmitLocationPermissionInfo(
            acceptedLocationPermission = hasCoarseLocationPermission && hasFineLocationPermission,
            showLocationRational = showLocationPermissionRational

        ))
        onAction(ActiveRunAction.SubmitNotificationPermissionInfo(
            acceptedNotificationPermission = hasNotificationPermission,
            showNotificationRational = showNotificationPermissionRational
        ))
    }
    
    LaunchedEffect(key1 = true) {
        val activity = context as ComponentActivity
        val showLocationPermissionRational = activity.shouldShowLocationPermissionRational()
        val showNotificationPermissionRational = activity.shouldShowNotificationPermissionRational()

        onAction(ActiveRunAction.SubmitLocationPermissionInfo(
            acceptedLocationPermission = context.hasLocationPermission(),
            showLocationRational = showLocationPermissionRational

        ))
        onAction(ActiveRunAction.SubmitNotificationPermissionInfo(
            acceptedNotificationPermission = context.hasNotificationPermission(),
            showNotificationRational = showNotificationPermissionRational
        ))

        if (!showLocationPermissionRational && !showNotificationPermissionRational) {
            permissionLauncher.requestRuniquePermission(context)
        }
    }

    RuniqueScaffold(
        withGradiant = false,
        topAppBar = {
            RuniqueToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.active_run),
                onBackClick = {
                    onAction(ActiveRunAction.OnBackClick)
                }
            )
        },
        floatingActionButton = {
            RuniqueFloatingActionButton(
                contentDescription = if (state.shouldTrack) {
                    stringResource(id = R.string.pause_run)
                } else {
                    stringResource(id = R.string.start_run)
                },
                icon = if (state.shouldTrack) {
                    StopIcon
                } else {
                    StartIcon
                },
                iconSize = 20.dp,
                onClick = {
                    onAction(ActiveRunAction.OnToggleRunClick)
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            RunDataCard(
                elapsedTime = state.elapsedTime,
                runData = state.runData,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(padding)
                    .fillMaxWidth()
            )
        }
    }

    if (state.showLocationRational || state.showNotificationRational) {
        RuniqueDialog(
            title = stringResource(id = R.string.permission_required),
            description = when {
                state.showLocationRational && state.showNotificationRational -> {
                    stringResource(id = R.string.location_notification_rationale)
                }
                state.showLocationRational -> {
                    stringResource(id = R.string.location_rationale)
                }
                else -> {
                    stringResource(id = R.string.notification_rationale)
                }

            },
            onDismiss = {},
            primaryButton = {
                RuniqueOutlinedActionButton(
                    text = stringResource(id = R.string.okay),
                    isLoading = false,
                    onClick = {
                        onAction(ActiveRunAction.DismissRationalDialog)
                        permissionLauncher.requestRuniquePermission(context)
                    }
                )
            }
        )
    }
}

private fun ActivityResultLauncher<Array<String>>.requestRuniquePermission(
    context : Context
) {
    val hasLocationPermission = context.hasLocationPermission()
    val hasNotification = context.hasNotificationPermission()

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val notificationPermission = if (Build.VERSION.SDK_INT >= 33) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        emptyArray()
    }

    when {
        !hasLocationPermission && !hasNotification -> {
            launch(locationPermissions + notificationPermission)
        }
        !hasLocationPermission -> launch(locationPermissions)
        else -> launch(notificationPermission)
    }
}

@Preview
@Composable
private fun ActiveRunScreenPreview() {
    RuniqueTheme {
        ActiveRunScreen(
            state = ActiveRunState(),
            onAction = {}
        )
    }
}

