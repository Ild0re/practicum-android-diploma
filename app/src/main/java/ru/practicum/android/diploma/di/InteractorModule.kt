package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.db.interactor.VacancyDbInteractor
import ru.practicum.android.diploma.domain.db.interactor.VacancyDbInteractorImpl
import ru.practicum.android.diploma.domain.usecases.base.SearchInteractor
import ru.practicum.android.diploma.domain.usecases.base.VacancyDetailInteractor
import ru.practicum.android.diploma.domain.usecases.impl.SearchInteractorImpl
import ru.practicum.android.diploma.domain.usecases.impl.VacancyDetailInteractorImpl

val interactorModule = module {
    single<SearchInteractor> { SearchInteractorImpl(get()) }
    single<VacancyDbInteractor> { VacancyDbInteractorImpl(get()) }
    single<VacancyDetailInteractor> { VacancyDetailInteractorImpl(get()) }
}
