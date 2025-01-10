package ru.practicum.android.diploma.domain.usecases.impl

import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.repository.FilterRepository
import ru.practicum.android.diploma.domain.repository.PreferenceRepository
import ru.practicum.android.diploma.domain.usecases.base.FilterInteractor
import ru.practicum.android.diploma.util.FilterField

class FilterInteractorImpl(
    private val slcRepository: PreferenceRepository,
    private val repository: FilterRepository
) : FilterInteractor {

    override fun getFilter(): Filter {
        val filter = repository.getFilter()
        val slcValue = slcRepository.getSalaryClose()
        return filter.copy(isOnlyWithSalary = slcValue)
    }

    override fun saveFilter(filter: Filter): Filter {
        slcRepository.saveSalaryClose(filter.isOnlyWithSalary)
        return repository.saveFilter(filter)
    }

    override fun updateFilter(field: FilterField, value: Any?): Filter {
        if (field == FilterField.ONLY_WITH_SALARY) {
            slcRepository.saveSalaryClose(value as Boolean)
        }
        return repository.updateFilter(field, value)
    }
}
