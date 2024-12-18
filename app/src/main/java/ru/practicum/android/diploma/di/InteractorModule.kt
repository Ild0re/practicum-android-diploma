package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.SearchInteractorImpl
import ru.practicum.android.diploma.domain.api.DetailedInformationInteractor
import ru.practicum.android.diploma.domain.impl.DetailedInformationInteractorImpl

val interactorModule = module {
    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }
    single<DetailedInformationInteractor> {
        DetailedInformationInteractorImpl(get())
    }
}
