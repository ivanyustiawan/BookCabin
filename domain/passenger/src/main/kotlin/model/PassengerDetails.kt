package model

data class PassengerDetails(
    val reservation: Reservation,
)

data class Reservation(
    val id: String,
    val passengers: Passengers
)

data class Passengers(
    val passenger: List<Passenger>,
)

data class Passenger(
    val id: String,
    val syntheticIdentifier: String,
    val personName: PersonName,
    val passengerSegments: PassengerSegments,
    val passengerDocument: List<PassengerDocument>,
    val emergencyContact: List<EmergencyContact>,
    val weightCategory: String,
)

data class PersonName(
    val prefix: String,
    val first: String,
    val last: String,
)

data class PassengerSegments(
    val passengerSegment: List<PassengerSegment>,
)

data class PassengerSegment(
    val passengerFlight: List<PassengerFlight>,
)

data class PassengerFlight(
    val boardingPass: BoardingPass
)

data class BoardingPass(
    val personName: PersonName,
    val flightDetail: FlightDetail,
    val displayData: DisplayData,
    val fareInfo: FareInfo,
    val group: String,
    val zone: String,
    val seat: Seat,
    val barCode: String,
)

data class FlightDetail(
    val airline: String,
    val flightNumber: String,
    val departureAirport: String,
    val arrivalAirport: String,
)

data class DisplayData(
    val departureAirportName: String,
    val arrivalAirportName: String,
)

data class PassengerDocument(
    val document: Document
)

data class Document(
    val id: String,
    val number: String,
    val personName: PersonName,
    val nationality: String,
    val dateOfBirth: String,
    val issuingCountry: String,
    val expiryDate: String,
    val gender: String,
    val type: String
)

data class EmergencyContact(
    val id: String,
    val name: String,
    val countryCode: String,
    val contactDetails: String,
    val relationship: String,
)

data class FareInfo(
    val bookingClass: String
)

data class Seat(
    val value: String
)
