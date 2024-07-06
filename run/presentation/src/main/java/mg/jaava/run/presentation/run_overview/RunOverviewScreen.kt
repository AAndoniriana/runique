@file:OptIn(ExperimentalMaterial3Api::class)

package mg.jaava.run.presentation.run_overview

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mg.jaava.core.presentationdesignsystem.AnalyticsIcon
import mg.jaava.core.presentationdesignsystem.LogoIcon
import mg.jaava.core.presentationdesignsystem.LogoutIcon
import mg.jaava.core.presentationdesignsystem.RunIcon
import mg.jaava.core.presentationdesignsystem.RuniqueTheme
import mg.jaava.core.presentationdesignsystem.component.RuniqueFloatingActionButton
import mg.jaava.core.presentationdesignsystem.component.RuniqueScaffold
import mg.jaava.core.presentationdesignsystem.component.RuniqueToolbar
import mg.jaava.core.presentationdesignsystem.component.utils.DropDownItem
import mg.jaava.run.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewScreenRoot(
    onStartRunClick: () -> Unit,
    viewModel: RunOverviewViewModel = koinViewModel()
) {
    RunOverviewScreen(
        onAction = { action ->
            when (action) {
                RunOverviewAction.OnStartClick -> onStartRunClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun RunOverviewScreen(
    onAction: (RunOverviewAction) -> Unit,
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    RuniqueScaffold(
        topAppBar = {
            RuniqueToolbar(
                title = stringResource(id = R.string.runique),
                showBackButton = false,
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                },
                menuItems = listOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(id = R.string.analytics)
                    ),
                    DropDownItem(
                        icon = LogoutIcon,
                        title = stringResource(id = R.string.logout)
                    )
                ),
                onMenuItemClick = {
                    when (it) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverviewAction.OnLogoutClick)
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            RuniqueFloatingActionButton(
                icon = RunIcon,
                onClick = {
                    onAction(RunOverviewAction.OnStartClick)
                },
                contentDescription = null
            )
        }
    ) { padding ->

    }
}

@Preview
@Composable
private fun RunOverviewPreview() {
    RuniqueTheme {
        RunOverviewScreen(
            onAction = {}
        )
    }
}