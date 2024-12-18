package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.search.SearchRepository
import ru.practicum.android.diploma.data.search.SearchRepositoryImpl

val repositoryModule = module {
    single<SearchRepository> {
        SearchRepositoryImpl(get(), get())
    }
    factory { VacancyDbConverter() }
}
