package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.DetailedInformationInteractor
import ru.practicum.android.diploma.domain.api.DetailedInformationRepository

class DetailedInformationInteractorImpl(private val repository: DetailedInformationRepository) : DetailedInformationInteractor {
    override fun shareUrlVacancy(urlVacancy: String) {
        repository.shareUrlVacancy(urlVacancy)
    }
}
