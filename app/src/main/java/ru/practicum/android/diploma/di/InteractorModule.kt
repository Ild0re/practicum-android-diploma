package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.db.interactor.VacancyDbInteractor
import ru.practicum.android.diploma.domain.db.interactor.VacancyDbInteractorImpl
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.SearchInteractorImpl

val interactorModule = module {
    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }
    single<VacancyDbInteractor> {
        VacancyDbInteractorImpl(get())
    }
}
