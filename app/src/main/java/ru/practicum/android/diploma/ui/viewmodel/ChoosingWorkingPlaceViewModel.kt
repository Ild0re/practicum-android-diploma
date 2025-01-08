package ru.practicum.android.diploma.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.usecases.base.FilterInteractor
import ru.practicum.android.diploma.domain.usecases.base.ReferencesIteractor
import ru.practicum.android.diploma.util.CountryState
import ru.practicum.android.diploma.util.FilterField

class ChoosingWorkingPlaceViewModel(
    private val interactor: FilterInteractor,
    private val referencesInteractor: ReferencesIteractor
) : ViewModel() {

    private var countryList: List<Area>? = null

    private val state = MutableLiveData<CountryState>()
    fun getState(): LiveData<CountryState> = state

    fun getFilter(): Filter {
        return interactor.getFilter()
    }

    private fun getCountryIds() {
        viewModelScope.launch {
            referencesInteractor.getCountries().collect { pair -> countryList = pair.first }
        }
    }

    fun getCountries(): List<Area>? {
        getCountryIds()
        return countryList
    }

    fun updateCountryFilter(country: Area) {
        interactor.updateFilter(FilterField.COUNTRY, country)
    }
}
