package model

data class PassengerBoardingPass(
    val boardingPasses: List<BoardingPasses>
)

data class BoardingPasses(
    val boardingPass: BoardingPass
)
