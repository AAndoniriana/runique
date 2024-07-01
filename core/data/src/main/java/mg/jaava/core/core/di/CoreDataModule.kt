package mg.jaava.core.core.di

import mg.jaava.core.core.auth.EncryptedSessionStorage
import mg.jaava.core.core.networking.HttpClientFactory
import mg.jaava.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}