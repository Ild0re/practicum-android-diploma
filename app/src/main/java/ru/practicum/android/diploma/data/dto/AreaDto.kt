package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class AreaDto(
    val id: String?,
    val name: String,
    val url: String?,
    @SerializedName("parent_id")
    val parentId: String?,
    val areas: List<AreaDto>?
) : Response()

/*data class AreaRegionsDto(
    val id: String,
    @SerializedName("parent_id")
    val parentId: String,
    val name: String,
    val areas: List<AreaRegionsDto?>,
)*/
