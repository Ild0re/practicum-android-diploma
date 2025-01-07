@file:Suppress("ImportOrdering")

package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.repositories.VacancyRepositoryImpl
import ru.practicum.android.diploma.domain.repository.VacancyRepository
import ru.practicum.android.diploma.data.db.repository.VacancyDbRepository
import ru.practicum.android.diploma.data.db.repository.VacancyDbRepositoryImpl
import ru.practicum.android.diploma.data.repositories.ReferencesRepositoryImpl
import ru.practicum.android.diploma.domain.repository.ReferencesRepository
import ru.practicum.android.diploma.data.filter.FilterRepositoryImpl
import ru.practicum.android.diploma.data.repositories.PreferenceRepositoryImpl
import ru.practicum.android.diploma.domain.repository.FilterRepository
import ru.practicum.android.diploma.domain.repository.PreferenceRepository

val repositoryModule = module {
    single<VacancyRepository> { VacancyRepositoryImpl(get(), get(), get(), get()) }
    single<VacancyDbRepository> { VacancyDbRepositoryImpl(get()) }
    single<ReferencesRepository> { ReferencesRepositoryImpl(get(), get()) }
    single<FilterRepository> { FilterRepositoryImpl(get()) }
    single<PreferenceRepository> { PreferenceRepositoryImpl(androidContext()) }
}
