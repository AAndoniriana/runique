package mg.jaava.data.di

import mg.jaava.auth.domain.AuthRepository
import mg.jaava.auth.domain.PatternValidator
import mg.jaava.auth.domain.UserDataValidator
import mg.jaava.data.AuthRepositoryImpl
import mg.jaava.data.EmailPatternValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}