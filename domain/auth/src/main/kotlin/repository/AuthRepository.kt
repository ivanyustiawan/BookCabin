package repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun getAuthToken(): Flow<Boolean>
}