package usecase

import kotlinx.coroutines.flow.Flow
import model.Gender
import model.PassengerDocument
import model.Results
import repository.PassengerRepository
import javax.inject.Inject

class GetPassengerUpdateUseCase @Inject constructor(private val repository: PassengerRepository) {
    suspend operator fun invoke(
        passportNumber: String,
        firstName: String,
        lastName: String,
        gender: Gender,
        contactName: String,
        countryCode: String,
        contactNumber: String,
        relationship: String,
        passengerDocumentList: List<PassengerDocument>,
        weightCategory: String,
        passengerId: String,
    ): Flow<Results> =
        repository.getPassengerUpdate(
            passportNumber,
            firstName,
            lastName,
            gender,
            contactName,
            countryCode,
            contactNumber,
            relationship,
            passengerDocumentList,
            weightCategory,
            passengerId,
        )
}