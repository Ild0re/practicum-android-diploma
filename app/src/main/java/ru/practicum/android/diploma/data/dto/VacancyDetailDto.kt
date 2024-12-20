@file:Suppress("all")
package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDetailDto(
    val id: String,
    @SerializedName("alternate_url")
    val alternateUrl: String,
    @SerializedName("apply_alternate_url")
    val applyAlternateUrl: String,
    val approved: Boolean,
    val archived: Boolean,
    val area: AreaDto,
    @SerializedName("branded_description")
    val brandedDescription: String,
    val code: String,
    @SerializedName("created_at")
    val createdAt: String,
    val description: String,
    val employer: EmployerDto,
    val employment: EmploymentDto,
    val experience: ExperienceDto,
    @SerializedName("initial_created_at")
    val initialCreatedAt: String,
    val internship: Boolean,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillsDto>,
    val name: String,
    @SerializedName("response_url")
    val responseUrl: Any?,
    val salary: SalaryDto?,
    val schedule: ScheduleDto,
    @SerializedName("work_format")
    val workFormat: List<WorkFormatDto>,
    val contacts:ContactsDto,
    val phones: PhonesDto)
