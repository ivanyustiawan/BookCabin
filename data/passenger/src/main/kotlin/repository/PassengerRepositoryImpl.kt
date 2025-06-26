package repository

import api.PassengerApi
import com.google.gson.Gson
import dto.ErrorResponse
import dto.PassengerBoardingPassRequest
import dto.PassengerCheckInRequest
import dto.PassengerDetailsRequest
import dto.PassengerDocumentRequest
import dto.PassengerUpdateRequest
import dto.ReservationCriteriaRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import local.TokenManager
import mapper.toModel
import mapper.updatePassengerDocument
import model.Gender
import model.PassengerBoardingPass
import model.PassengerCheckIn
import model.PassengerDetails
import model.PassengerDocument
import model.Results
import javax.inject.Inject

class PassengerRepositoryImpl @Inject constructor(
    private val passengerApi: PassengerApi,
    private val tokenManager: TokenManager,
) : PassengerRepository {

    override suspend fun getPassengerDetails(
        pnr: String,
        lastName: String
    ): Flow<PassengerDetails> = flow {

        val bodyRequest = PassengerDetailsRequest(
            reservationCriteria = ReservationCriteriaRequest(
                recordLocator = pnr,
                lastName = lastName
            ),
            outputFormat = "BPXML"
        )

        val response = passengerApi.getPassengerDetails(request = bodyRequest)
        if (response.isSuccessful) {
            tokenManager.saveSessionId(response.headers()["session-id"].orEmpty())
            emit(response.body().toModel())
        } else {
            val errorJson = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorJson, ErrorResponse::class.java)

            throw Exception(errorResponse.message)
        }

    }.catch { e ->
        throw Exception(e.message)
    }

    override suspend fun getPassengerUpdate(
        passportNumber: String,
        firstName: String,
        lastName: String,
        gender: Gender,
        passengerDocumentList: List<PassengerDocument>,
        weightCategory: String,
        passengerId: String,
    ): Flow<Results> = flow {

        val bodyRequest = PassengerUpdateRequest(
            returnSession = false,
            passengerDetails = listOf(
                PassengerDocumentRequest(
                    documents = passengerDocumentList.map {
                        it.updatePassengerDocument(passportNumber, firstName, lastName, gender)
                    },
                    weightCategory = weightCategory,
                    passengerId = passengerId,
                )
            )
        )
        val response = passengerApi.getPassengerUpdate(request = bodyRequest)
        if (response.isSuccessful) {
            emit(response.body().toModel())
        } else {
            val errorJson = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorJson, ErrorResponse::class.java)
            throw Exception(errorResponse.message)
        }

    }.catch { e ->
        throw Exception(e.message)
    }

    override suspend fun getPassengerCheckIn(passengerIds: String): Flow<PassengerCheckIn> = flow {

        val bodyRequest = PassengerCheckInRequest(
            returnSession = false,
            passengerIds = listOf(passengerIds),
            outputFormat = "BPXML",
            waiveAutoReturnCheckIn = false
        )

        val response = passengerApi.getPassengerCheckIn(request = bodyRequest)
        if (response.isSuccessful) {
            emit(response.body().toModel())
        } else {
            val errorJson = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorJson, ErrorResponse::class.java)

            throw Exception(errorResponse.message)
        }
    }.catch { e ->
        throw Exception(e.message)
    }

    override suspend fun getPassengerBoardingPass(flightId: String): Flow<PassengerBoardingPass> =
        flow {
            val bodyRequest = PassengerBoardingPassRequest(
                returnSession = false,
                passengerFlightIds = listOf(flightId),
                outputFormat = "BPXML",
            )

            val response = passengerApi.getPassengerBoardingPass(request = bodyRequest)
            if (response.isSuccessful) {
                emit(response.body().toModel())
            } else {
                val errorJson = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorJson, ErrorResponse::class.java)

                throw Exception(errorResponse.message)
            }
        }.catch { e ->
            throw Exception(e.message)
        }
}

