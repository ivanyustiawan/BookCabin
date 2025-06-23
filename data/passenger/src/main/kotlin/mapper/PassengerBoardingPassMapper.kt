package mapper

import dto.BoardingPassesResponse
import dto.PassengerBoardingPassResponse
import model.BoardingPasses
import model.PassengerBoardingPass

fun PassengerBoardingPassResponse?.toModel(): PassengerBoardingPass = PassengerBoardingPass(
    boardingPasses = this?.boardingPasses?.map { it.toModel() }.orEmpty()
)

fun BoardingPassesResponse?.toModel(): BoardingPasses = BoardingPasses(
    boardingPass = this?.boardingPass.toModel()
)

