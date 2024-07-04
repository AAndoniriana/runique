package mg.jaava.auth.domain

import mg.jaava.core.domain.util.DataError
import mg.jaava.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}