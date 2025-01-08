package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.network.HeadHunterWebApiClient
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.util.handleResponse
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.repository.ReferencesRepository
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.mappers.toArea
import ru.practicum.android.diploma.util.mappers.toIndustry

class ReferencesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val webApiClient: HeadHunterWebApiClient
) : ReferencesRepository {
    override suspend fun getCountries(): Flow<Resource<List<Area>>> = flow {
        val response = networkClient.executeRequest {
            webApiClient.getCountries()
        }

        emit(handleResponse(response, ::toAreaList))
    }

    override suspend fun getRegions(country: Area): Flow<Resource<Area>> = flow {
        val response = networkClient.executeRequest {
            webApiClient.getRegions(country.id)
        }

        emit(handleResponse(response, ::toArea))
    }

    override suspend fun getIndistries(): Flow<Resource<List<Industry>>> = flow {
        var response = networkClient.executeRequest {
            webApiClient.getIndustries()
        }

        emit(handleResponse(response, ::toIndustryList))
    }

    override suspend fun getRegion(country: String): Flow<Resource<Area>> = flow {
        val response = networkClient.executeRequest {
            webApiClient.getRegions(country)
        }

        emit(handleResponse(response, ::toArea))
    }

    private fun toArea(areaListDto: AreaDto): Area {
        return areaListDto.toArea()
    }

    private fun toAreaList(areaListDto: List<AreaDto>): List<Area> {
        return areaListDto.map { areaDto -> areaDto.toArea() }
    }

    private fun toIndustryList(industryListDto: List<IndustryDto>): List<Industry> {
        return industryListDto.map { industry -> industry.toIndustry() }
    }
}
