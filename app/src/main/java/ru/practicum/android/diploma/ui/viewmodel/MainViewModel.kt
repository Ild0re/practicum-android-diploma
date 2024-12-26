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
import ru.practicum.android.diploma.domain.models.VacancyList
import ru.practicum.android.diploma.domain.usecases.base.FilterInteractor
import ru.practicum.android.diploma.domain.usecases.base.SearchInteractor
import ru.practicum.android.diploma.util.ScreenState

class MainViewModel(
    private val searchInteractor: SearchInteractor,
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    companion object {
        const val SERVER_ERROR = "Ошибка сервера"
        const val NO_INTERNET = "Нет интернета"
        const val VACANCIES_LOAD_ERROR = "Не удалось получить список вакансий"
        const val NOTHING_FOUND = "Ничего не нашлось"
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val FILTER_DEBOUNCE_DELAY = 100L
        private const val START_PAGE = 0
        private const val START_MAX_PAGE = 1
    }

    init {
        getFilter()
    }

    private var currentPage = START_PAGE
    private var maxPage = START_MAX_PAGE

    private val state = MutableLiveData<ScreenState>()
    private val filterFlagState = MutableLiveData<Boolean>()
    private var textInput = ""
    private var searchJob: Job? = null
    private var isNextPageLoading = false

    fun getState(): LiveData<ScreenState> = state
    fun getFilterFlagState(): LiveData<Boolean> = filterFlagState

    fun loadData(expression: String, page: Int) {
        clearJob()
        state.value = ScreenState.Loading

        viewModelScope.launch {
            searchInteractor
                .getAllVacancies(expression, page)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(data: VacancyList?, errorMessage: String?) {
        val vacancies = mutableListOf<Vacancy>()
        if (data != null) {
            vacancies.addAll(data.item)
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
                val content = ScreenState.Content(vacancies, data?.found.toString())
                state.postValue(content)
            }
        }
    }

    fun searchDebounce(expression: String) {
        if (textInput == expression) {
            return
        }
        textInput = expression
        clearJob()
        if (expression.isNotBlank()) {
            searchJob = viewModelScope.launch {
                delay(SEARCH_DEBOUNCE_DELAY)
                loadData(expression, currentPage)
            }
        }
    }

    fun searchVacancies(expression: String) {
        if (currentPage < maxPage && !isNextPageLoading && maxPage != 1) {
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

    fun clearJob() {
        searchJob?.cancel()
    }

    fun getFilter() {
        var filterIconFlag = false
        viewModelScope.launch {
            val filter = filterInteractor.getFilter()
            delay(FILTER_DEBOUNCE_DELAY)
            if (listOf(
                    filter.country,
                    filter.area,
                    filter.scope,
                    filter.salary
                ).any { it != null } || filter.isOnlyWithSalary
            ) {
                filterIconFlag = true
                filterFlagState.postValue(true)
            } else {
                filterIconFlag = false
                filterFlagState.postValue(false)
            }
        }
    }
}
