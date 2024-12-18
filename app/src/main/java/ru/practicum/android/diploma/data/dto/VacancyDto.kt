package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

class VacancyDto(
    val id: String,
    val name: String,
    val area: AreaDto,
    val employer: EmployerDto,
    val salary: SalaryDto?,
    val url: String,
    val snippet: SnippetDto,
    val experience: ExperienceDto?,
    @SerializedName("employment_form")
    val employmentForm: EmploymentFormDto?,
    val schedule: ScheduleDto?
)
