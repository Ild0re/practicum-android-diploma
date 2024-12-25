package ru.practicum.android.diploma.domain.usecases.base

import ru.practicum.android.diploma.domain.models.Filter

interface FilterInteractor {
    fun getFilter(): Filter
    fun saveFilter(filter: Filter): Filter
}
