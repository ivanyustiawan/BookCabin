package dto

import com.google.gson.annotations.SerializedName

data class PassengerDetailsResponse(
    @SerializedName("reservation") val reservation: ReservationResponse?,
)

data class ReservationResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("passengers") val passengers: PassengersResponse?,
)

data class PassengersResponse(
    @SerializedName("passenger") val passenger: List<PassengerResponse>?,
)

data class PassengerResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("syntheticIdentifier") val syntheticIdentifier: String?,
    @SerializedName("personName") val personName: PersonNameResponse?,
    @SerializedName("passengerSegments") val passengerSegments: PassengerSegmentsResponse?,
    @SerializedName("passengerDocument") val passengerDocument: List<PassengerDocumentResponse>?,
    @SerializedName("emergencyContact") val emergencyContact: List<EmergencyContactResponse>?,
    @SerializedName("weightCategory") val weightCategory: String?,
)

data class PersonNameResponse(
    @SerializedName("prefix") val prefix: String?,
    @SerializedName("first") val first: String?,
    @SerializedName("last") val last: String?,
)

data class PassengerSegmentsResponse(
    @SerializedName("passengerSegment") val passengerSegment: List<PassengerSegmentResponse>?,
)

data class PassengerSegmentResponse(
    @SerializedName("passengerFlight") val passengerFlight: List<PassengerFlightResponse>?,
)

data class PassengerFlightResponse(
    @SerializedName("boardingPass") val boardingPass: BoardingPassResponse?
)

data class BoardingPassResponse(
    @SerializedName("personName") val personName: PersonNameResponse?,
    @SerializedName("flightDetail") val flightDetail: FlightDetailResponse?,
    @SerializedName("displayData") val displayData: DisplayDataResponse?,
    @SerializedName("fareInfo") val fareInfo: FareInfoResponse?,
    @SerializedName("group") val group: String?,
    @SerializedName("zone") val zone: String?,
    @SerializedName("seat") val seatResponse: SeatResponse?,
    @SerializedName("barCode") val barCode: String?,
)

data class FlightDetailResponse(
    @SerializedName("airline") val airline: String?,
    @SerializedName("flightNumber") val flightNumber: String?,
    @SerializedName("departureAirport") val departureAirport: String?,
    @SerializedName("arrivalAirport") val arrivalAirport: String?,
    @SerializedName("departureTime") val departureTime: String?,
    @SerializedName("arrivalTime") val arrivalTime: String?,
    @SerializedName("boardingTime") val boardingTime: String?,
    @SerializedName("departureGate") val departureGate: String?,
)

data class DisplayDataResponse(
    @SerializedName("departureAirportName") val departureAirportName: String?,
    @SerializedName("arrivalAirportName") val arrivalAirportName: String?,
)

data class FareInfoResponse(
    @SerializedName("bookingClass") val bookingClass: String?,
)

data class SeatResponse(
    @SerializedName("value") val value: String?
)

data class EmergencyContactResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("countryCode") val countryCode: String?,
    @SerializedName("contactDetails") val contactDetails: String?,
    @SerializedName("relationship") val relationship: String?,
)

data class PassengerDocumentResponse(
    @SerializedName("document") val document: DocumentResponse?
)

data class DocumentResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("number") val number: String?,
    @SerializedName("personName") val personName: PersonNameResponse?,
    @SerializedName("nationality") val nationality: String?,
    @SerializedName("dateOfBirth") val dateOfBirth: String?,
    @SerializedName("issuingCountry") val issuingCountry: String?,
    @SerializedName("expiryDate") val expiryDate: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("type") val type: String?
)
