package mg.jaava.data

import io.ktor.client.HttpClient
import mg.jaava.auth.domain.AuthRepository
import mg.jaava.core.core.networking.post
import mg.jaava.core.domain.util.DataError
import mg.jaava.core.domain.util.EmptyResult

class AuthRepositoryImpl(
    private val httpClient: HttpClient
) : AuthRepository {
    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }
}