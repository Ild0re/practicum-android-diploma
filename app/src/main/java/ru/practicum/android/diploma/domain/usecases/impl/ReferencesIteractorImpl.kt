package ru.practicum.android.diploma.domain.usecases.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.network.flowResultHandler
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.repository.ReferencesRepository
import ru.practicum.android.diploma.domain.usecases.base.ReferencesIteractor

class ReferencesIteractorImpl(private val areaRepository: ReferencesRepository) : ReferencesIteractor {
    override suspend fun getCountries(): Flow<List<Area>?> {
        return areaRepository.getCountries().map { result -> flowResultHandler(result) }
    }

    override suspend fun getRegions(country: Area): Flow<List<Area>?> {
        return areaRepository.getRegions(country).map { result -> flowResultHandler(result) }
    }

    override suspend fun getIndustries(): Flow<List<Industry>?> {
        return areaRepository.getIndistries().map { result -> flowResultHandler(result) }
    }
}
