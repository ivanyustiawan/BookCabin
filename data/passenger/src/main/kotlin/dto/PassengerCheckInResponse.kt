package dto

import com.google.gson.annotations.SerializedName

data class PassengerCheckInResponse(
    @SerializedName("results") val results: List<ResultResponse>?
)

data class ResultsResponse(
    @SerializedName("results") val results: List<ResultResponse>?
)

data class ResultResponse(
    @SerializedName("status") val status: List<StatusResponse>?,
    @SerializedName("passengerFlightRef") val passengerFlightRef: String?,
    @SerializedName("update") val update: UpdateResponse?
)

data class StatusResponse(
    @SerializedName("type") val type: String?
)

data class UpdateResponse(
    @SerializedName("value") val type: String?,
    @SerializedName("context") val context: String?,
    @SerializedName("operation") val operation: String?
)