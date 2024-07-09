package mg.jaava.run.domain

import kotlinx.coroutines.flow.Flow
import mg.jaava.core.domain.location.LocationWithAltitude

interface LocationObserver {
    fun observeLocation(interval: Long): Flow<LocationWithAltitude>
}