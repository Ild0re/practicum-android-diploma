package ru.practicum.android.diploma.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.usecases.base.ReferencesIteractor
import ru.practicum.android.diploma.util.IndustryState

class ChoosingIndustryViewModel(private val referencesIteractor: ReferencesIteractor) : ViewModel() {
    companion object {
        const val VACANCIES_LOAD_ERROR = "Не удалось получить список вакансий"
        const val NOTHING_FOUND = "Ничего не нашлось"
    }

    private val state = MutableLiveData<IndustryState>()
    fun getState(): LiveData<IndustryState> = state
    fun loadData() {
        viewModelScope.launch {
            referencesIteractor.getIndustries().collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(data: List<Industry>?, errorMessage: String?) {
        val vacancies = mutableListOf<Industry>()
        if (data != null) {
            vacancies.addAll(data)
        }
        when {
            errorMessage == MainViewModel.VACANCIES_LOAD_ERROR -> {
                val error = IndustryState.Error(VACANCIES_LOAD_ERROR)
                state.postValue(error)
            }

            vacancies.isEmpty() -> {
                val empty = IndustryState.Empty(NOTHING_FOUND)
                state.postValue(empty)
            }

            else -> {
                val content = IndustryState.Content(data, FavoriteViewModel.NOT_USE)
                state.postValue(content)
            }
        }
    }
}
