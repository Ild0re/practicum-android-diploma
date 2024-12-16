package ru.practicum.android.diploma.ui.main

import android.util.Log
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
            errorMessage == "Ошибка сервера" -> {
                val error = ScreenState.Error("Ошибка сервера")
                Log.e("ERROR", errorMessage)
                state.postValue(error)
            }

            errorMessage == "Нет интернета" -> {
                val error = ScreenState.Error("Нет интернета")
                Log.e("ERROR", errorMessage)
                state.postValue(error)
            }

            errorMessage == "Не удалось получить список вакансий" -> {
                val error = ScreenState.Error("Не удалось получить список вакансий")
                Log.e("ERROR", errorMessage)
                state.postValue(error)
            }

            vacancies.isEmpty() -> {
                val empty = ScreenState.Empty("Ничего не нашлось")
                state.postValue(empty)
            }

            else -> {
                val content = ScreenState.Content(vacancies)
                state.postValue(content)
            }
        }
    }
}
