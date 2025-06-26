package model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PassengerDetails(
    val reservation: Reservation,
) : Parcelable

@Parcelize
data class Reservation(
    val id: String,
    val passengers: Passengers
) : Parcelable

@Parcelize
data class Passengers(
    val passenger: List<Passenger>,
) : Parcelable

@Parcelize
data class Passenger(
    val id: String,
    val syntheticIdentifier: String,
    val personName: PersonName,
    val passengerSegments: PassengerSegments,
    val passengerDocument: List<PassengerDocument>,
    val emergencyContact: List<EmergencyContact>,
    val weightCategory: String,
) : Parcelable

@Parcelize
data class PersonName(
    val prefix: String,
    val first: String,
    val last: String,
) : Parcelable

@Parcelize
data class PassengerSegments(
    val passengerSegment: List<PassengerSegment>,
) : Parcelable

@Parcelize
data class PassengerSegment(
    val passengerFlight: List<PassengerFlight>,
) : Parcelable

@Parcelize
data class PassengerFlight(
    val boardingPass: BoardingPass
) : Parcelable

@Parcelize
data class BoardingPass(
    val personName: PersonName,
    val flightDetail: FlightDetail,
    val displayData: DisplayData,
    val fareInfo: FareInfo,
    val group: String,
    val zone: String,
    val seat: Seat,
    val barCode: String,
) : Parcelable

@Parcelize
data class FlightDetail(
    val airline: String,
    val flightNumber: String,
    val departureAirport: String,
    val arrivalAirport: String,
    val departureTime: String,
    val arrivalTime: String,
    val boardingTime: String,
    val departureGate: String,
) : Parcelable

@Parcelize
data class DisplayData(
    val departureAirportName: String,
    val arrivalAirportName: String,
) : Parcelable

@Parcelize
data class PassengerDocument(
    val document: Document
) : Parcelable

@Parcelize
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
) : Parcelable

@Parcelize
data class EmergencyContact(
    val id: String,
    val name: String,
    val countryCode: String,
    val contactDetails: String,
    val relationship: String,
) : Parcelable

@Parcelize
data class FareInfo(
    val bookingClass: String
) : Parcelable

@Parcelize
data class Seat(
    val value: String
) : Parcelable
