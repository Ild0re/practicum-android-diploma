package ru.practicum.android.diploma.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.db.interactor.VacancyDbInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.ScreenState

class FavoriteViewModel(val vacancyDbInteractor: VacancyDbInteractor) : ViewModel() {
    companion object {
        const val VACANCIES_LOAD_ERROR = "Не удалось получить список вакансий"
        const val NOTHING_FOUND = "Ничего не нашлось"
        const val NOT_USE = ""
    }

    private val state = MutableLiveData<ScreenState>()
    fun getState(): LiveData<ScreenState> = state

    fun loadData() {
        viewModelScope.launch {
            vacancyDbInteractor.getVacancy().collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(data: List<Vacancy>?, errorMessage: String?) {
        val vacancies = mutableListOf<Vacancy>()
        if (data != null) {
            vacancies.addAll(data)
        }
        when {
            errorMessage == MainViewModel.VACANCIES_LOAD_ERROR -> {
                val error = ScreenState.Error(VACANCIES_LOAD_ERROR)
                state.postValue(error)
            }

            vacancies.isEmpty() -> {
                val empty = ScreenState.Empty(NOTHING_FOUND)
                state.postValue(empty)
            }

            else -> {
                val content = ScreenState.Content(vacancies, NOT_USE)
                state.postValue(content)
            }
        }
    }
}
