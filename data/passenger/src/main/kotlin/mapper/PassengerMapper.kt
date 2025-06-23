package mapper

import dto.BoardingPassResponse
import dto.DisplayDataResponse
import dto.DocumentResponse
import dto.EmergencyContactResponse
import dto.FareInfoResponse
import dto.FlightDetailResponse
import dto.PassengerDetailsResponse
import dto.PassengerDocumentResponse
import dto.PassengerFlightResponse
import dto.PassengerResponse
import dto.PassengerSegmentResponse
import dto.PassengerSegmentsResponse
import dto.PassengersResponse
import dto.PersonNameResponse
import dto.ReservationResponse
import dto.SeatResponse
import model.BoardingPass
import model.DisplayData
import model.Document
import model.EmergencyContact
import model.FareInfo
import model.FlightDetail
import model.Passenger
import model.PassengerDetails
import model.PassengerDocument
import model.PassengerFlight
import model.PassengerSegment
import model.PassengerSegments
import model.Passengers
import model.PersonName
import model.Reservation
import model.Seat

fun PassengerDetailsResponse?.toModel(): PassengerDetails = PassengerDetails(
    reservation = this?.reservation.toModel(),
)

fun ReservationResponse?.toModel(): Reservation = Reservation(
    id = this?.id.orEmpty(),
    passengers = this?.passengers.toModel()
)

fun PassengersResponse?.toModel(): Passengers = Passengers(
    passenger = this?.passenger?.map { it.toModel() }.orEmpty()
)

fun PassengerResponse?.toModel(): Passenger = Passenger(
    id = this?.id.orEmpty(),
    syntheticIdentifier = this?.syntheticIdentifier.orEmpty(),
    personName = this?.personName.toModel(),
    passengerSegments = this?.passengerSegments.toModel(),
    passengerDocument = this?.passengerDocument?.map { it.toModel() }.orEmpty(),
    emergencyContact = this?.emergencyContact?.map { it.toModel() }.orEmpty(),
    weightCategory = this?.weightCategory.orEmpty()
)

fun PersonNameResponse?.toModel(): PersonName = PersonName(
    prefix = this?.prefix.orEmpty(),
    first = this?.first.orEmpty(),
    last = this?.last.orEmpty(),
)

fun PassengerSegmentsResponse?.toModel(): PassengerSegments = PassengerSegments(
    passengerSegment = this?.passengerSegment?.map { it.toModel() }.orEmpty()
)

fun PassengerSegmentResponse?.toModel(): PassengerSegment = PassengerSegment(
    passengerFlight = this?.passengerFlight?.map { it.toModel() }.orEmpty()
)

fun PassengerFlightResponse?.toModel(): PassengerFlight = PassengerFlight(
    boardingPass = this?.boardingPass.toModel()
)

fun BoardingPassResponse?.toModel(): BoardingPass = BoardingPass(
    personName = this?.personName.toModel(),
    flightDetail = this?.flightDetail.toModel(),
    displayData = this?.displayData.toModel(),
    fareInfo = this?.fareInfo.toModel(),
    group = this?.group.orEmpty(),
    zone = this?.zone.orEmpty(),
    seat = this?.seatResponse.toModel(),
    barCode = this?.barCode.orEmpty()
)

fun FlightDetailResponse?.toModel(): FlightDetail = FlightDetail(
    airline = this?.airline.orEmpty(),
    flightNumber = this?.flightNumber.orEmpty(),
    departureAirport = this?.departureAirport.orEmpty(),
    arrivalAirport = this?.arrivalAirport.orEmpty()
)

fun DisplayDataResponse?.toModel(): DisplayData = DisplayData(
    departureAirportName = this?.departureAirportName.orEmpty(),
    arrivalAirportName = this?.arrivalAirportName.orEmpty()
)

fun PassengerDocumentResponse?.toModel(): PassengerDocument = PassengerDocument(
    document = this?.document.toModel()
)

fun DocumentResponse?.toModel(): Document = Document(
    id = this?.id.orEmpty(),
    number = this?.id.orEmpty(),
    personName = this?.personName.toModel(),
    nationality = this?.nationality.orEmpty(),
    dateOfBirth = this?.dateOfBirth.orEmpty(),
    issuingCountry = this?.issuingCountry.orEmpty(),
    expiryDate = this?.expiryDate.orEmpty(),
    gender = this?.gender.orEmpty(),
    type = this?.type.orEmpty(),
)

fun EmergencyContactResponse?.toModel(): EmergencyContact = EmergencyContact(
    id = this?.id.orEmpty(),
    name = this?.name.orEmpty(),
    countryCode = this?.countryCode.orEmpty(),
    contactDetails = this?.contactDetails.orEmpty(),
    relationship = this?.relationship.orEmpty(),
)

fun FareInfoResponse?.toModel(): FareInfo = FareInfo(
    bookingClass = this?.bookingClass.orEmpty()
)

fun SeatResponse?.toModel(): Seat = Seat(
    value = this?.value.orEmpty()
)
