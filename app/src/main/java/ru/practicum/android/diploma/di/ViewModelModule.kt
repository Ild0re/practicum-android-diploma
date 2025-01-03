package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.viewmodel.ChoosingCountryViewModel
import ru.practicum.android.diploma.ui.viewmodel.ChoosingRegionViewModel
import ru.practicum.android.diploma.ui.viewmodel.ChoosingIndustryViewModel
import ru.practicum.android.diploma.ui.viewmodel.ChoosingWorkingPlaceViewModel
import ru.practicum.android.diploma.ui.viewmodel.FavoriteViewModel
import ru.practicum.android.diploma.ui.viewmodel.FilterSettingViewModel
import ru.practicum.android.diploma.ui.viewmodel.MainViewModel
import ru.practicum.android.diploma.ui.viewmodel.VacancyDetailViewModel

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { (id: String) -> VacancyDetailViewModel(get(), id, get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { FilterSettingViewModel(get(), get()) }
    viewModel { ChoosingCountryViewModel(get(), get()) }
    viewModel { ChoosingRegionViewModel(get(), get()) }
    viewModel { ChoosingIndustryViewModel(get(), get()) }
    viewModel { ChoosingWorkingPlaceViewModel(get()) }
}
