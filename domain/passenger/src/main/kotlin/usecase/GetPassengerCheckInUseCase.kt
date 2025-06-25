package usecase

import kotlinx.coroutines.flow.Flow
import model.PassengerCheckIn
import repository.PassengerRepository
import javax.inject.Inject

class GetPassengerCheckInUseCase @Inject constructor(private val repository: PassengerRepository) {
    suspend operator fun invoke(passengerIds: String): Flow<PassengerCheckIn> =
        repository.getPassengerCheckIn(passengerIds)
}