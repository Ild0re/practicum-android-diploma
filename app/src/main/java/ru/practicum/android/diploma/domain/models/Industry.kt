package ru.practicum.android.diploma.domain.models

class Industry(
    val id: String,
    val name: String,
    val click: Boolean = false,
    val industries: List<Industry>? = null
)
