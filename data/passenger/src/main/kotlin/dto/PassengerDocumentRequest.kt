package dto

import model.Document
import model.EmergencyContact

data class PassengerUpdateRequest(
    val returnSession: Boolean,
    val passengerDetails: List<PassengerDocumentRequest>,
)

data class PassengerDocumentRequest(
    val documents: List<DocumentsRequest>?,
    val emergencyContacts: List<EmergencyContact>?,
    val weightCategory: String,
    val passengerId: String
)

data class DocumentsRequest(
    val documentRequest: Document
)
