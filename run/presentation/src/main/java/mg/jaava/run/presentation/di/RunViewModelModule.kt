package mg.jaava.run.presentation.di

import mg.jaava.run.presentation.active_run.ActiveRunViewModel
import mg.jaava.run.presentation.run_overview.RunOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val runViewModelModule = module {
    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)
}