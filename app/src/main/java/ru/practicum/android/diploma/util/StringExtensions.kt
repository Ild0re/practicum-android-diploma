package ru.practicum.android.diploma.util

fun String.stripHtml(): String {

    var result = this.replace(Regex("<[^>]*>"), "")

    val htmlEntities = mapOf(
        "&nbsp;" to " ",
        "&amp;" to "&",
        "&lt;" to "<",
        "&gt;" to ">",
        "&quot;" to "\"",
        "&apos;" to "'",
        "&#x27;" to "'",
        "&#x2F;" to "/",
        "&#39;" to "'",
        "&#47;" to "/"
    )

    htmlEntities.forEach { (entity, char) ->
        result = result.replace(entity, char)
    }

    result = result.replace(Regex("\\s+"), " ").trim()

    return result
}
