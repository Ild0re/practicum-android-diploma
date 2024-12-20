@file:Suppress("all")

package ru.practicum.android.diploma.ui.viewmodel

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.di.viewModelModule
import ru.practicum.android.diploma.domain.db.interactor.VacancyDbInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.usecases.base.VacancyDetailInteractor
import ru.practicum.android.diploma.ui.viewmodel.FavoriteViewModel.Companion
import ru.practicum.android.diploma.ui.viewmodel.FavoriteViewModel.Companion.NOTHING_FOUND
import ru.practicum.android.diploma.ui.viewmodel.FavoriteViewModel.Companion.NOT_USE
import ru.practicum.android.diploma.util.ScreenState
import ru.practicum.android.diploma.util.VacancyState

class VacancyDetailViewModel(
    private val interactor: VacancyDetailInteractor,
    val id: String,
    private val favouritesInteractor: VacancyDbInteractor,
) : ViewModel() {

    init {
        getFavouritesIds()
    }

    private lateinit var vacancy: Vacancy
    private var vacanciesIdsListFromDb: List<String> = emptyList()

    companion object {
        const val SERVER_ERROR = "Ошибка сервера"
        const val NO_INTERNET = "Нет интернета"
        const val VACANCIES_LOAD_ERROR = "Не удалось получить список вакансий"
    }

    private val vacancyState = MutableLiveData<VacancyState>()
    private val favouriteState = MutableLiveData<Boolean>()

    fun observeVacancyState(): LiveData<VacancyState> = vacancyState
    fun observeFavourites(): LiveData<Boolean> = favouriteState

    private fun loadData() {
        viewModelScope.launch {
            interactor.getVacancyById(id)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(data: Vacancy?, errorMessage: String?) {
        if (data != null) {
            vacancy = data
            favouriteCheck()
        }
        when (errorMessage) {
            SERVER_ERROR -> {
                if (id in vacanciesIdsListFromDb) {
                    loadDataFromDb()
                } else {
                    val error = VacancyState.Error(SERVER_ERROR)
                    vacancyState.postValue(error)
                }
            }

            NO_INTERNET -> {
                if (id in vacanciesIdsListFromDb) {
                    loadDataFromDb()
                } else {
                    val error = VacancyState.Error(NO_INTERNET)
                    vacancyState.postValue(error)
                }
            }

            VACANCIES_LOAD_ERROR -> {
                val error = VacancyState.Error(VACANCIES_LOAD_ERROR)
                deleteVacancy()
                vacancyState.postValue(error)
            }

            else -> {
                val content = VacancyState.Content(vacancy)
                vacancyState.postValue(content)
            }
        }
    }

    fun onFavouriteClicked() {
        viewModelScope.launch {
            getFavouritesIds()
            if (id !in vacanciesIdsListFromDb) {
                favouritesInteractor.insertVacancy(vacancy.copy(inFavorite = true))
                favouriteState.postValue(true)
            } else {
                favouritesInteractor.deleteVacancy(vacancy.copy(inFavorite = false))
                favouriteState.postValue(false)
            }
        }
    }

    private fun favouriteCheck() {
        if (!vacancy.inFavorite) {
            favouriteState.postValue(false)
        } else {
            favouriteState.postValue(true)
        }
    }

    private fun deleteVacancy() {
        viewModelScope.launch {
            favouritesInteractor.deleteVacancy(vacancy.copy(inFavorite = false))
            favouriteState.postValue(false)
        }
    }

    private fun loadDataFromDb() {
        viewModelScope.launch {
            favouritesInteractor.getVacancyById(id).collect { data ->
                processFromDb(data)
            }
        }
    }

    private fun processFromDb(data: Vacancy) {
        vacancy = data
        favouriteCheck()
        val content = VacancyState.Content(vacancy)
        vacancyState.postValue(content)
    }

    private fun getFavouritesIds() {
        viewModelScope.launch {
            favouritesInteractor.getVacancyIds().collect { data ->
                getIdsFromDb(data)
                loadData()
            }
        }
    }

    private fun getIdsFromDb(data: List<String>) {
        vacanciesIdsListFromDb = data
    }

    fun shareVacancy(): Intent {
        Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${vacancy.contacts}")
        }
        Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${vacancy.phones}")
        }
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            vacancy.url
        )
        shareIntent.setType("text/plain")
        val intentChooser = Intent.createChooser(shareIntent, "")
        return intentChooser
    }
}
