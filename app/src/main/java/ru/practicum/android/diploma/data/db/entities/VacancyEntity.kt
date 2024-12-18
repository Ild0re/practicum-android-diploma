package ru.practicum.android.diploma.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey
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

