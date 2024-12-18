package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.search.SearchRepository
import ru.practicum.android.diploma.data.search.SearchRepositoryImpl
import ru.practicum.android.diploma.domain.api.DetailedInformationRepository
import ru.practicum.android.diploma.data.impl.DetailedInformationRepositotyImpl


val repositoryModule = module {
    single<SearchRepository> {
        SearchRepositoryImpl(get(), get())
    }
    single<DetailedInformationRepository> {
        DetailedInformationRepositotyImpl(get())
    }
}
