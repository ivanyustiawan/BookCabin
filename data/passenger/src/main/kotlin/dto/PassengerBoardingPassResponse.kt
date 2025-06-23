package dto

import com.google.gson.annotations.SerializedName

data class PassengerBoardingPassResponse(
    @SerializedName("boardingPasses") val boardingPasses: List<BoardingPassesResponse>?,
)

data class BoardingPassesResponse(
    @SerializedName("boardingPass") val boardingPass: BoardingPassResponse?,
)

