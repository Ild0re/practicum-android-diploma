package ru.practicum.android.diploma.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.usecases.base.ReferencesIteractor
import ru.practicum.android.diploma.util.CountryState

class ChoosingRegionViewModel(private val referencesIteractor: ReferencesIteractor) : ViewModel() {
    companion object {
        const val VACANCIES_LOAD_ERROR = "Не удалось получить список вакансий"
        const val NOTHING_FOUND = "Ничего не нашлось"
    }

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
        val vacancies = mutableListOf<Area>()
        if (data != null) {
            vacancies.addAll(data)
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
                val content = CountryState.Content(data, FavoriteViewModel.NOT_USE)
                state.postValue(content)
            }
        }
    }
}
