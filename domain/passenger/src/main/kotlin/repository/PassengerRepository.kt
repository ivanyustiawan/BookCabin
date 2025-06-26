package repository

import kotlinx.coroutines.flow.Flow
import model.Gender
import model.PassengerBoardingPass
import model.PassengerCheckIn
import model.PassengerDetails
import model.PassengerDocument
import model.Results

interface PassengerRepository {
    suspend fun getPassengerDetails(pnr: String, lastName: String): Flow<PassengerDetails>
    suspend fun getPassengerUpdate(
        passportNumber: String,
        firstName: String,
        lastName: String,
        gender: Gender,
        contactName: String,
        countryCode: String,
        contactNumber: String,
        relationship: String,
        passengerDocumentList: List<PassengerDocument>,
        weightCategory: String,
        passengerId: String,
    ): Flow<Results>

    suspend fun getPassengerCheckIn(passengerIds: String): Flow<PassengerCheckIn>
    suspend fun getPassengerBoardingPass(flightId: String): Flow<PassengerBoardingPass>
}