@file:Suppress("all")

package ru.practicum.android.diploma.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.usecases.base.VacancyDetailInteractor
import ru.practicum.android.diploma.util.VacancyState

class VacancyDetailViewModel(private val interactor: VacancyDetailInteractor, val id: String) : ViewModel() {

    init {
        loadData()
    }

    companion object {
        const val SERVER_ERROR = "Ошибка сервера"
        const val NO_INTERNET = "Нет интернета"
        const val VACANCIES_LOAD_ERROR = "Не удалось получить список вакансий"
    }

    private val vacancyState = MutableLiveData<VacancyState>()

    fun observeVacancyState(): LiveData<VacancyState> = vacancyState

    fun loadData() {
        viewModelScope.launch {
            interactor.getVacancyById(id)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(data: Vacancy?, errorMessage: String?) {
        lateinit var vacancy: Vacancy
        if (data != null) {
            vacancy = data
        }
        when (errorMessage) {
            SERVER_ERROR -> {
                val error = VacancyState.Error(SERVER_ERROR)
                vacancyState.postValue(error)
            }
            NO_INTERNET -> {
                val error = VacancyState.Error(NO_INTERNET)
                vacancyState.postValue(error)
            }
            VACANCIES_LOAD_ERROR -> {
                val error = VacancyState.Error(VACANCIES_LOAD_ERROR)
                vacancyState.postValue(error)
            }
            else -> {
                val content = VacancyState.Content(vacancy)
                vacancyState.postValue(content)
            }
        }
    }
}
