package dto

data class PassengerBoardingPassRequest(
    val returnSession: Boolean,
    val passengerFlightIds: List<String>,
    val outputFormat: String,
)