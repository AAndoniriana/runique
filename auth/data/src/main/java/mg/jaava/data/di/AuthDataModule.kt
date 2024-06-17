package mg.jaava.data.di

import mg.jaava.auth.domain.PatternValidator
import mg.jaava.auth.domain.UserDataValidator
import mg.jaava.data.EmailPatternValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
}