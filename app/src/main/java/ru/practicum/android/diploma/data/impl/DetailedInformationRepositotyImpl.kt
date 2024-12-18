package ru.practicum.android.diploma.data.impl

import ru.practicum.android.diploma.domain.api.DetailedInformationRepository
import ru.practicum.android.diploma.domain.api.ExternalNavigator

class DetailedInformationRepositotyImpl(
    private val externalNavigator: ExternalNavigator
): DetailedInformationRepository {
    override fun shareUrlVacancy(urlVacancy: String) {
        externalNavigator.shareUrlVacancy(urlVacancy)
    }
}
