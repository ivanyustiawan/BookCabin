package dto

import model.EmergencyContact
import model.PassengerDocument

data class PassengerUpdateRequest(
    val returnSession: Boolean,
    val passengerDetails: List<PassengerDocumentRequest>,
)

data class PassengerDocumentRequest(
    val documents: List<PassengerDocument>,
    val emergencyContacts: List<EmergencyContact>,
    val weightCategory: String,
    val passengerId: String
)
