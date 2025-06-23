package dto

data class PassengerDetailsRequest(
    val reservationCriteria: ReservationCriteriaRequest,
    val outputFormat: String
)

data class ReservationCriteriaRequest(
    val recordLocator: String,
    val lastName: String
)