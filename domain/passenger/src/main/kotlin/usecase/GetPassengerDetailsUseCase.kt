package usecase

import kotlinx.coroutines.flow.Flow
import model.PassengerDetails
import repository.PassengerRepository
import javax.inject.Inject

class GetPassengerDetailsUseCase @Inject constructor(private val repository: PassengerRepository) {
    suspend operator fun invoke(pnr: String, lastName: String): Flow<PassengerDetails> =
        repository.getPassengerDetails(pnr, lastName)
}