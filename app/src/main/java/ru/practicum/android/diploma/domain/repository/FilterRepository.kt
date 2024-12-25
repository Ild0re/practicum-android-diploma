package ru.practicum.android.diploma.domain.repository

import ru.practicum.android.diploma.domain.models.Filter

interface FilterRepository {

    fun getFilter(): Filter
    fun saveFilter(filter: Filter): Filter
}
