package repository

import kotlinx.coroutines.flow.Flow
import model.PassengerBoardingPass
import model.PassengerCheckIn
import model.PassengerDetails
import model.Results

interface PassengerRepository {
    suspend fun getPassengerDetails(pnr: String, lastName: String): Flow<PassengerDetails>
    suspend fun getPassengerUpdate(
        weightCategory: String,
        passengerId: String,
    ): Flow<Results>
    suspend fun getPassengerCheckIn(passengerIds: String): Flow<PassengerCheckIn>
    suspend fun getPassengerBoardingPass(flightId: String): Flow<PassengerBoardingPass>
}