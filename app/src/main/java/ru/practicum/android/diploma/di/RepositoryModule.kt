package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.converter.VacancyDbConverter
import ru.practicum.android.diploma.data.db.repository.VacancyDbRepository
import ru.practicum.android.diploma.data.db.repository.VacancyDbRepositoryImpl
import ru.practicum.android.diploma.data.search.SearchRepository
import ru.practicum.android.diploma.data.search.SearchRepositoryImpl

val repositoryModule = module {
    single<SearchRepository> {
        SearchRepositoryImpl(get(), get(), get(), get())
    }
    factory { VacancyDbConverter() }
    single<VacancyDbRepository> {
        VacancyDbRepositoryImpl(get(), get())
    }
}
