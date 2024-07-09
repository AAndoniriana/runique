package mg.jaava.run.presentation.di

import mg.jaava.run.domain.RunningTracker
import mg.jaava.run.presentation.active_run.ActiveRunViewModel
import mg.jaava.run.presentation.run_overview.RunOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val runPresentationModule = module {
    singleOf(::RunningTracker)

    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)
}