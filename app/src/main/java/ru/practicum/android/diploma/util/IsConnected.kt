package ru.practicum.android.diploma.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun isConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    var connectFlag = false

    if (capabilities != null) {
        connectFlag = connectFlag or capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        connectFlag = connectFlag or capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        connectFlag = connectFlag or capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
    return connectFlag
}
