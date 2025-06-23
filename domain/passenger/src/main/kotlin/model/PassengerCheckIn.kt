package model

data class PassengerCheckIn(
    val results: List<Result>
)

data class Results(
    val results: List<Result>
)

data class Result(
    val status: List<Status>,
    val passengerFlightRef: String,
    val update: Update,
)

data class Status(
    val type: String
)

data class Update(
    val type: String,
    val context: String,
    val operation: String
)