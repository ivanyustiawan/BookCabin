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
import model.Gender
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
    passengerDocument = this?.passengerDocument?.map { it.toModel(this.personName) }.orEmpty(),
    emergencyContact = this?.emergencyContact?.map { it.toModel() }.orEmpty(),
    weightCategory = this?.weightCategory.orEmpty()
)

fun PersonNameResponse?.toModel(personNameResponse: PersonNameResponse? = null): PersonName =
    PersonName(
        prefix = if (this?.prefix.isNullOrBlank()) personNameResponse?.prefix.orEmpty() else this.prefix,
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
    arrivalAirport = this?.arrivalAirport.orEmpty(),
    departureTime = this?.departureTime.orEmpty(),
    arrivalTime = this?.arrivalTime.orEmpty(),
    boardingTime = this?.boardingTime.orEmpty(),
    departureGate = this?.departureGate.orEmpty()
)

fun DisplayDataResponse?.toModel(): DisplayData = DisplayData(
    departureAirportName = this?.departureAirportName.orEmpty(),
    arrivalAirportName = this?.arrivalAirportName.orEmpty()
)

fun PassengerDocumentResponse?.toModel(personNameResponse: PersonNameResponse? = null): PassengerDocument =
    PassengerDocument(
        document = this?.document.toModel(personNameResponse)
    )

fun DocumentResponse?.toModel(personNameResponse: PersonNameResponse? = null): Document = Document(
    id = this?.id.orEmpty(),
    number = this?.id.orEmpty(),
    personName = this?.personName.toModel(personNameResponse),
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

fun PassengerDocument.updatePassengerDocument(
    passportNumber: String,
    firstName: String,
    lastName: String,
    gender: Gender,
): PassengerDocument = PassengerDocument(
    document = this.document.updateDocument(
        passportNumber,
        firstName,
        lastName,
        gender
    )
)

fun Document.updateDocument(
    passportNumber: String,
    firstName: String,
    lastName: String,
    gender: Gender,
): Document = Document(
    id = this.id,
    number = passportNumber,
    personName = this.personName.updatePersonName(gender, firstName, lastName),
    nationality = this.nationality,
    dateOfBirth = this.dateOfBirth,
    issuingCountry = this.issuingCountry,
    expiryDate = this.expiryDate,
    gender = gender.gender,
    type = this.type,
)

fun PersonName.updatePersonName(gender: Gender, firstName: String, lastName: String): PersonName =
    PersonName(
        prefix = gender.prefix,
        first = firstName,
        last = lastName,
    )
