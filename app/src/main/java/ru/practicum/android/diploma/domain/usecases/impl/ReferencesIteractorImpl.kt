package ru.practicum.android.diploma.domain.usecases.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.repository.ReferencesRepository
import ru.practicum.android.diploma.domain.usecases.base.ReferencesIteractor
import ru.practicum.android.diploma.util.Resource

class ReferencesIteractorImpl(private val areaRepository: ReferencesRepository) : ReferencesIteractor {
    override suspend fun getCountries(): Flow<Pair<List<Area>?, String?>> {
        return areaRepository.getCountries().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override suspend fun getRegions(country: Area): Flow<Pair<List<Area>?, String?>> {
        return areaRepository.getRegions(country).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data?.areas, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override suspend fun getIndustries(): Flow<Pair<List<Industry>?, String?>> {
        return areaRepository.getIndistries().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override suspend fun getRegion(country: String): Flow<Pair<Area?, String?>> {
        return areaRepository.getRegion(country).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}

