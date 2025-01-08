package ru.practicum.android.diploma.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.usecases.base.FilterInteractor
import ru.practicum.android.diploma.domain.usecases.base.ReferencesIteractor
import ru.practicum.android.diploma.util.CountryState
import ru.practicum.android.diploma.util.FilterField

class ChoosingRegionViewModel(
    private val referencesIteractor: ReferencesIteractor,
    private val interactor: FilterInteractor
) : ViewModel() {
    companion object {
        const val VACANCIES_LOAD_ERROR = "Не удалось получить список вакансий"
        const val NOTHING_FOUND = "Ничего не нашлось"
        const val NOT_USE = ""
        const val DELAY = 100L
    }

    private val vacancies = mutableListOf<Area>()
    private var countries: List<Area>? = null

    private val state = MutableLiveData<CountryState>()
    fun getState(): LiveData<CountryState> = state
    fun loadData(country: Area) {
        viewModelScope.launch {
            referencesIteractor.getRegions(country).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(data: List<Area>?, errorMessage: String?) {
        if (data != null) {
            for (i in data) {
                if (i.areas.isNotEmpty()) {
                    vacancies.addAll(i.areas)
                    loadData(i)
                } else {
                    vacancies.add(i)
                }
            }
        }
        when {
            errorMessage == MainViewModel.VACANCIES_LOAD_ERROR -> {
                val error = CountryState.Error(VACANCIES_LOAD_ERROR)
                state.postValue(error)
            }

            vacancies.isEmpty() -> {
                val empty = CountryState.Empty(NOTHING_FOUND)
                state.postValue(empty)
            }

            else -> {
                val content = CountryState.Content(vacancies, NOT_USE)
                state.postValue(content)
            }
        }
    }

    fun updateRegionFilter(region: Area) {
        interactor.updateFilter(FilterField.REGION, region)
    }

    fun getFilter(): Filter {
        return interactor.getFilter()
    }

    private fun getCountries() {
        viewModelScope.launch {
            referencesIteractor.getCountries().collect { pair ->
                countries = pair.first
            }
        }
    }

    fun getAllRegions() {
        viewModelScope.launch {
            getCountries()
            delay(DELAY)
            if (countries != null) {
                for (i in countries!!) {
                    loadData(i)
                }
            }
        }
    }
}
