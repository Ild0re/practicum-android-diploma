package ru.practicum.android.diploma.domain.models

class Vacancy(val items: List<VacancyItem>?,
              val found: Int,
              val page: Int,
              val pages: Int,
              val perPages: Int) {
}
