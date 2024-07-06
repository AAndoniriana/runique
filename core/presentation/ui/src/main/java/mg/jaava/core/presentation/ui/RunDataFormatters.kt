package mg.jaava.core.presentation.ui

import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.time.Duration

fun Duration.formatted(): String {
    val totalSeconds = inWholeSeconds
    val hours = "%02d".format(totalSeconds / 3600)
    val minutes = "%02d".format((totalSeconds % 3600) / 60)
    val seconds = "%02d".format(totalSeconds % 60)
    return "$hours:$minutes:$seconds"
}

fun Double.toFormattedKilometers(): String = "${roundToDecimals(1)}"

fun Duration.toFormattedPace(distanceKm: Double): String {
    if (this == Duration.ZERO || distanceKm <= 0.0) {
        return "-"
    }
    val secondsPerKm = (inWholeSeconds / distanceKm).roundToInt()
    val avgPaceMinutes = secondsPerKm / 60
    val avgPaceSeconds = "%02d".format(secondsPerKm % 60)
    return "$avgPaceMinutes:$avgPaceSeconds / km"
}

private fun Double.roundToDecimals(decimalCount: Int): Double {
    val factor = 10f.pow(decimalCount)
    return round(this * factor) / factor
}