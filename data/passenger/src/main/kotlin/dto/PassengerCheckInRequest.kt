package dto

data class PassengerCheckInRequest(
    val returnSession: Boolean,
    val passengerIds: List<String>,
    val outputFormat: String,
    val waiveAutoReturnCheckIn: Boolean,
)