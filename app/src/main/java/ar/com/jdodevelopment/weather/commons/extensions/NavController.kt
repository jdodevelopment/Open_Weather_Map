package ar.com.jdodevelopment.weather.commons.extensions

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.navigateSafe(@IdRes destinationId: Int, navDirection: NavDirections) {
    if (currentDestination?.id == destinationId) {
        navigate(navDirection)
    }
}
