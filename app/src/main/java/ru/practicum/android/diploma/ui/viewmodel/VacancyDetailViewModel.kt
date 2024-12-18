package ru.practicum.android.diploma.ui.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.usecases.base.VacancyDetailInteractor

class VacancyDetailViewModel(private val interactor: VacancyDetailInteractor): ViewModel() {}
