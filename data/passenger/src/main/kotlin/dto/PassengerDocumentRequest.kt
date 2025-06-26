package dto

import model.PassengerDocument

data class PassengerUpdateRequest(
    val returnSession: Boolean,
    val passengerDetails: List<PassengerDocumentRequest>,
)

data class PassengerDocumentRequest(
    val documents: List<PassengerDocument>,
    val weightCategory: String,
    val passengerId: String
)
