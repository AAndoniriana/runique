package mg.jaava.data

import io.ktor.client.HttpClient
import mg.jaava.auth.domain.AuthRepository
import mg.jaava.core.core.networking.post
import mg.jaava.core.domain.AuthInfo
import mg.jaava.core.domain.SessionStorage
import mg.jaava.core.domain.util.DataError
import mg.jaava.core.domain.util.EmptyResult
import mg.jaava.core.domain.util.Result
import mg.jaava.core.domain.util.asEmptyDataResult

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
) : AuthRepository {
    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if (result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }

        return result.asEmptyDataResult()
    }

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