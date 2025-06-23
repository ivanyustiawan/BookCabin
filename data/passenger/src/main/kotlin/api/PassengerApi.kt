package api

import dto.PassengerBoardingPassRequest
import dto.PassengerBoardingPassResponse
import dto.PassengerCheckInRequest
import dto.PassengerCheckInResponse
import dto.PassengerDetailsRequest
import dto.PassengerDetailsResponse
import dto.PassengerDocumentRequest
import dto.PassengerUpdateRequest
import dto.ResultsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface PassengerApi {

    @POST("v918/dcci/passenger/details")
    suspend fun getPassengerDetails(
        @Query("jipcc") jipcc: String = "ODCI",
        @Body request: PassengerDetailsRequest,
    ): Response<PassengerDetailsResponse>

    @POST("v918/dcci/passenger/update")
    suspend fun getPassengerUpdate(
        @Query("jipcc") jipcc: String = "ODCI",
        @Body request: PassengerUpdateRequest,
    ): Response<ResultsResponse>

    @POST("v918/dcci/passenger/checkin")
    suspend fun getPassengerCheckIn(
        @Query("jipcc") jipcc: String = "ODCI",
        @Body request: PassengerCheckInRequest,
    ): Response<PassengerCheckInResponse>

    @POST("v918/dcci/passenger/boardingpass")
    suspend fun getPassengerBoardingPass(
        @Query("jipcc") jipcc: String = "ODCI",
        @Body request: PassengerBoardingPassRequest,
    ): Response<PassengerBoardingPassResponse>

}