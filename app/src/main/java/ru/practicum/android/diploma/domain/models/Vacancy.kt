package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val name: String,
    val areaName: String,
    val employerName: String,
    val employerLogo: String,
    val url: String,
    val salaryFrom: String,
    val salaryTo: String,
    val salaryCurrency: String,
    val scheduleName: String,
    val snippetRequirement: String,
    val snippetResponsibility: String,
    val experienceName: String,
    val inFavorite: Boolean,
    val keySkill: String
)
