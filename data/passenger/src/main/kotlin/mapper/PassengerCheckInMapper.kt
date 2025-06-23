package mapper

import dto.PassengerCheckInResponse
import dto.ResultResponse
import dto.ResultsResponse
import dto.StatusResponse
import dto.UpdateResponse
import model.PassengerCheckIn
import model.Result
import model.Results
import model.Status
import model.Update

fun PassengerCheckInResponse?.toModel(): PassengerCheckIn = PassengerCheckIn(
    results = this?.results?.map { it.toModel() }.orEmpty()
)

fun ResultsResponse?.toModel(): Results = Results(
    results = this?.results?.map { it.toModel() }.orEmpty()
)

fun ResultResponse?.toModel(): Result = Result(
    status = this?.status?.map { it.toModel() }.orEmpty(),
    passengerFlightRef = this?.passengerFlightRef.orEmpty(),
    update = this?.update.toModel()
)

fun StatusResponse?.toModel(): Status = Status(
    type = this?.type.orEmpty()
)

fun UpdateResponse?.toModel(): Update = Update(
    type = this?.type.orEmpty(),
    context = this?.context.orEmpty(),
    operation = this?.operation.orEmpty(),
)