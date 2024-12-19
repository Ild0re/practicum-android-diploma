package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.viewmodel.FavoriteViewModel
import ru.practicum.android.diploma.ui.viewmodel.MainViewModel
import ru.practicum.android.diploma.ui.viewmodel.VacancyDetailViewModel

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { VacancyDetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}
