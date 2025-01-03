package ru.practicum.android.diploma.domain.usecases.impl

import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.repository.FilterRepository
import ru.practicum.android.diploma.domain.usecases.base.FilterInteractor
import ru.practicum.android.diploma.util.FilterField

class FilterInteractorImpl(private val repository: FilterRepository) : FilterInteractor {

    override fun getFilter(): Filter {
        return repository.getFilter()
    }

    override fun saveFilter(filter: Filter): Filter {
        return repository.saveFilter(filter)
    }

    override fun updateFilter(field: FilterField, value: Any?): Filter {
        return repository.updateFilter(field, value)
    }
}
