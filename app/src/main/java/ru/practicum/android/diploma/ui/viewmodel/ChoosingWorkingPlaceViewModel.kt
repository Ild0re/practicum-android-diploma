package ru.practicum.android.diploma.ui.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.usecases.base.FilterInteractor

class ChoosingWorkingPlaceViewModel(private val interactor: FilterInteractor): ViewModel() {
    fun getFilter(): Filter {
        return interactor.getFilter()
    }
}
