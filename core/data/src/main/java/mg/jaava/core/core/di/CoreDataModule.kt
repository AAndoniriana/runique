package mg.jaava.core.core.di

import mg.jaava.core.core.networking.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
}