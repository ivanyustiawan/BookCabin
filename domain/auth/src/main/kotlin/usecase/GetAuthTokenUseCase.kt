package usecase

import kotlinx.coroutines.flow.Flow
import repository.AuthRepository
import javax.inject.Inject

class GetAuthTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(): Flow<Boolean> = repository.getAuthToken()
}