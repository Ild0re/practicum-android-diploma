package ru.practicum.android.diploma.data.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.ExternalNavigator

class ExternalNavigatorImpl(private val  context: Context) : ExternalNavigator {
    override fun shareUrlVacancy(urlVacancy: String) {
        val agreementIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(urlVacancy)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(agreementIntent)
    }
}
