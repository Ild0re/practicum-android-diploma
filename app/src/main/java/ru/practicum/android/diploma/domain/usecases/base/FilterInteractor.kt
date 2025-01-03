package ru.practicum.android.diploma.domain.usecases.base

import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.util.FilterField

interface FilterInteractor {
    fun getFilter(): Filter
    fun saveFilter(filter: Filter): Filter
    fun updateFilter(field: FilterField, value: Any?): Filter
}
