package ru.practicum.android.diploma.ui.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.DetailedInformationInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class DetailedInformationViewModel(
    private val interactor: DetailedInformationInteractor
) : ViewModel() {

private val vacancy: Vacancy? = null

    //LiveData

    fun shareVacancyUrl() {
        if (vacancy != null) {
     interactor.shareUrlVacancy(vacancy.url)}
    }
}
