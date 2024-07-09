package mg.jaava.run.location

import android.location.Location
import mg.jaava.core.domain.location.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude =
    LocationWithAltitude(
        location = mg.jaava.core.domain.location.Location(
            lat = latitude,
            long = longitude
        ),
        altitude = altitude

    )