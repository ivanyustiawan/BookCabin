package dateHelper

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatIsoDate(input: String, outputPattern: String = "dd MMM yyyy, HH:mm"): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(input)
        val formatter = DateTimeFormatter.ofPattern(outputPattern, Locale.getDefault())
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        "-"
    }
}