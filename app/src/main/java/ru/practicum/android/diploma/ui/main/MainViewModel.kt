package ru.practicum.android.diploma.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.util.ScreenState

class MainViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

    companion object {
        const val SERVER_ERROR = "Ошибка сервера"
        const val NO_INTERNET = "Нет интернета"
        const val VACANCIES_LOAD_ERROR = "Не удалось получить список вакансий"
        const val NOTHING_FOUND = "Ничего не нашлось"
    }

    private val state = MutableLiveData<ScreenState>()

    fun getState(): LiveData<ScreenState> = state

    fun loadData(expression: String, page: Int) {
        state.value = ScreenState.Loading

        viewModelScope.launch {
            searchInteractor
                .getAllVacancies(expression, page)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(data: List<VacancyDto>?, errorMessage: String?) {
        // заменить VacancyDto на модель Vacancy
        val vacancies = mutableListOf<VacancyDto>()
        if (data != null) {
            vacancies.addAll(data)
        }
        when {
            errorMessage == SERVER_ERROR -> {
                val error = ScreenState.Error(SERVER_ERROR)
                state.postValue(error)
            }

            errorMessage == NO_INTERNET -> {
                val error = ScreenState.Error(NO_INTERNET)
                state.postValue(error)
            }

            errorMessage == VACANCIES_LOAD_ERROR -> {
                val error = ScreenState.Error(VACANCIES_LOAD_ERROR)
                state.postValue(error)
            }

            vacancies.isEmpty() -> {
                val empty = ScreenState.Empty(NOTHING_FOUND)
                state.postValue(empty)
            }

            else -> {
                val content = ScreenState.Content(vacancies)
                state.postValue(content)
            }
        }
    }
}
