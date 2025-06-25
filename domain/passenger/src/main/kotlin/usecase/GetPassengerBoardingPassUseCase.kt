package usecase

import kotlinx.coroutines.flow.Flow
import model.PassengerBoardingPass
import repository.PassengerRepository
import javax.inject.Inject

class GetPassengerBoardingPassUseCase @Inject constructor(private val repository: PassengerRepository) {
    suspend operator fun invoke(flightId: String): Flow<PassengerBoardingPass> =
        repository.getPassengerBoardingPass(flightId)
}