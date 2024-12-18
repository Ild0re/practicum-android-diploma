package ru.practicum.android.diploma.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyItem
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
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val START_PAGE = 0
        private const val START_MAX_PAGE = 1
    }

    var currentPage = START_PAGE
    var maxPage = START_MAX_PAGE

    private val state = MutableLiveData<ScreenState>()
    private var textInput = ""
    private var searchJob: Job? = null
    private var isNextPageLoading = false

    fun getState(): LiveData<ScreenState> = state

    fun loadData(expression: String, page: Int) {
        searchJob?.cancel()
        state.value = ScreenState.Loading

        viewModelScope.launch {
            searchInteractor
                .getAllVacancies(expression, page)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(data: Vacancy?, errorMessage: String?) {
        val vacancies = mutableListOf<VacancyItem>()
        if (data != null) {
            vacancies.forEach { vacancies.add(it) }
            maxPage = data.pages
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

    fun searchDebounce(expression: String) {
        if (textInput == expression) {
            return
        }
        textInput = expression
        searchJob?.cancel()
        if (expression.isNotBlank()) {
            searchJob = viewModelScope.launch {
                delay(SEARCH_DEBOUNCE_DELAY)
                loadData(expression, currentPage)
            }
        }
    }

    fun searchVacancies(expression: String) {
        if (currentPage != maxPage && !isNextPageLoading) {
            isNextPageLoading = true
            viewModelScope.launch {
                currentPage++
                delay(SEARCH_DEBOUNCE_DELAY)
                val searching = async { loadData(expression, currentPage) }
                searching.await()
                isNextPageLoading = false
            }
        }
    }
}
