package usecase

import kotlinx.coroutines.flow.Flow
import model.PassengerDocument
import model.Results
import repository.PassengerRepository
import javax.inject.Inject

class GetPassengerUpdateUseCase @Inject constructor(private val repository: PassengerRepository) {
    suspend operator fun invoke(
        passengerDocumentList: List<PassengerDocument>,
        weightCategory: String,
        passengerId: String,
    ): Flow<Results> =
        repository.getPassengerUpdate(passengerDocumentList, weightCategory, passengerId)
}