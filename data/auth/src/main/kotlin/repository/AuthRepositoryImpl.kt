package repository

import api.AuthApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import local.TokenManager
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager,
) : AuthRepository {
    override suspend fun getAuthToken(): Flow<Boolean> = flow {
        val response = authApi.getAccessToken()

        if (response.isSuccessful) {
            val token = response.body()?.accessToken ?: throw Exception("Token is empty")
            tokenManager.saveAccessToken(token)
            emit(true)
        } else {
            throw Exception("Token is empty")
        }
    }.catch { e ->
        throw Exception("Error " + e.message)
    }
}