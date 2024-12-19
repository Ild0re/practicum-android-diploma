package ru.practicum.android.diploma.di

import org.koin.dsl.module

import ru.practicum.android.diploma.data.search.VacancyRepositoryImpl
import ru.practicum.android.diploma.domain.repository.VacancyRepository
import ru.practicum.android.diploma.data.db.repository.VacancyDbRepository
import ru.practicum.android.diploma.data.db.repository.VacancyDbRepositoryImpl

val repositoryModule = module {
    single<VacancyRepository> { VacancyRepositoryImpl(get(), get(), get()) }
    single<VacancyDbRepository> {  VacancyDbRepositoryImpl(get(), get()) }
}
