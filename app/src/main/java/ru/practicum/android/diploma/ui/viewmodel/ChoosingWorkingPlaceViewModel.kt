package ru.practicum.android.diploma.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val state = MutableLiveData<CountryState>()
    fun getState(): LiveData<CountryState> = state

    private val _countryListNew = MutableStateFlow<List<Area>?>(null)
    private val _region = MutableStateFlow<Area?>(null)
    val regionNew: StateFlow<Area?> get() = _region

    fun loadCountries() {
        viewModelScope.launch {
            referencesInteractor.getCountries().collect { pair ->
                _countryListNew.value = pair.first
            }
        }
    }

    fun getFilter(): Filter {
        return interactor.getFilter()
    }

    fun updateCountryFilter(country: Area) {
        interactor.updateFilter(FilterField.COUNTRY, country)
    }

    fun loadFilterRegion(region: String) {
        viewModelScope.launch {
            referencesInteractor.getRegion(region).collect { pair ->
                _region.value = pair.first
            }
        }
    }
}
