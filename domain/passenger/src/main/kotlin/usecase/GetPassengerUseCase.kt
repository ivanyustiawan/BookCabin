package usecase

import kotlinx.coroutines.flow.Flow
import model.PassengerBoardingPass
import model.PassengerCheckIn
import model.PassengerDetails
import model.PassengerDocument
import model.Results
import repository.PassengerRepository
import javax.inject.Inject

class GetPassengerUseCase @Inject constructor(private val repository: PassengerRepository) {

    suspend fun getPassengerDetails(pnr: String, lastName: String): Flow<PassengerDetails> =
        repository.getPassengerDetails(pnr, lastName)

    suspend fun getPassengerUpdate(
        passengerDocumentList: List<PassengerDocument>,
        weightCategory: String,
        passengerId: String,
    ): Flow<Results> =
        repository.getPassengerUpdate(passengerDocumentList, weightCategory, passengerId)

    suspend fun getPassengerCheckIn(passengerIds: String): Flow<PassengerCheckIn> =
        repository.getPassengerCheckIn(passengerIds)

    suspend fun getPassengerBoardingPass(flightId: String): Flow<PassengerBoardingPass> =
        repository.getPassengerBoardingPass(flightId)
}