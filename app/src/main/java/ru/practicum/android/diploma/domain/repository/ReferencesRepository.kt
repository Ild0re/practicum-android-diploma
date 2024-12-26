package ru.practicum.android.diploma.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

interface ReferencesRepository {
    suspend fun getCountries(): Flow<Resource<List<Area>>>
    suspend fun getRegions(country: Area): Flow<Resource<List<Area>>>
    suspend fun getIndistries(): Flow<Resource<List<Industry>>>
}
