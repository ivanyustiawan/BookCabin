package dto

data class ErrorResponse(
    val status: String,
    val type: String,
    val errorCode: String,
    val timeStamp: String,
    val message: String
)