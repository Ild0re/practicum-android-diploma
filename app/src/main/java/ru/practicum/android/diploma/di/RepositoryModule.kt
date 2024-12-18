package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.repository.VacancyRepository
import ru.practicum.android.diploma.data.search.VacancyRepositoryImpl

val repositoryModule = module {
    single<VacancyRepository> {
        VacancyRepositoryImpl(get(), get())
    }
}
