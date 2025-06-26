package com.example.bookcabin.screen.checkIn

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookcabin.R
import dateHelper.formatIsoDate
import model.PassengerDetails
import uiState.AppUiState

@Composable
fun CheckInScreen(
    passengerDetails: PassengerDetails?,
    viewModel: CheckInViewModel = hiltViewModel(),
    onCheckSuccess: (String) -> Unit
) {
    val view = LocalView.current
    val window = (view.context as? Activity)?.window

    val checkInState by viewModel.checkInState.collectAsState()
    val context = LocalContext.current

    if (window != null) {
        SideEffect {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    passengerDetails?.let {
        CheckInView(viewModel, passengerDetails)
    }

    when (checkInState) {
        is AppUiState.Idle -> {}
        is AppUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is AppUiState.Success -> {
            val flightId = (checkInState as AppUiState.Success<String>).data
            if (flightId.isNotEmpty()) {
                onCheckSuccess.invoke(flightId)
            } else {
                Toast.makeText(
                    context,
                    "Error: Not Success",
                    Toast.LENGTH_SHORT
                ).show()
            }
            viewModel.setState(AppUiState.Idle)
        }

        is AppUiState.Error -> {
            Toast.makeText(
                context,
                (checkInState as AppUiState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@Composable
fun CheckInView(
    viewModel: CheckInViewModel,
    passengerDetails: PassengerDetails
) {
    val airlane =
        passengerDetails.reservation.passengers.passenger[0].passengerSegments.passengerSegment[0].passengerFlight[0].boardingPass.flightDetail.airline
    val flightNumber =
        passengerDetails.reservation.passengers.passenger[0].passengerSegments.passengerSegment[0].passengerFlight[0].boardingPass.flightDetail.flightNumber
    val departureAirport =
        passengerDetails.reservation.passengers.passenger[0].passengerSegments.passengerSegment[0].passengerFlight[0].boardingPass.displayData.departureAirportName
    val arrivalAirport =
        passengerDetails.reservation.passengers.passenger[0].passengerSegments.passengerSegment[0].passengerFlight[0].boardingPass.displayData.arrivalAirportName
    val departureTime =
        passengerDetails.reservation.passengers.passenger[0].passengerSegments.passengerSegment[0].passengerFlight[0].boardingPass.flightDetail.departureTime
    val arrivalTime =
        passengerDetails.reservation.passengers.passenger[0].passengerSegments.passengerSegment[0].passengerFlight[0].boardingPass.flightDetail.arrivalTime
    val boardingTime =
        passengerDetails.reservation.passengers.passenger[0].passengerSegments.passengerSegment[0].passengerFlight[0].boardingPass.flightDetail.boardingTime
    val gate =
        passengerDetails.reservation.passengers.passenger[0].passengerSegments.passengerSegment[0].passengerFlight[0].boardingPass.flightDetail.departureGate

    val passengerId = passengerDetails.reservation.passengers.passenger[0].id
    val name =
        "${passengerDetails.reservation.passengers.passenger[0].personName.first} ${passengerDetails.reservation.passengers.passenger[0].personName.last}"
    val info = "$airlane $flightNumber  $departureAirport to $arrivalAirport"

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.secondary,
        bottomBar = {
            Button(
                onClick = {
                    viewModel.getPassengerCheckIn(passengerId)
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 64.dp)
                    .height(48.dp)
            ) {
                Text(text = stringResource(id = R.string.button_check_in))
            }
        }) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(144.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(id = R.string.title_check_in),
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.labelLarge,
            )

            Text(
                text = info,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(id = R.string.label_departure_time),
                style = MaterialTheme.typography.labelLarge,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 0.dp, top = 0.dp, bottom = 0.dp),
                text = formatIsoDate(departureTime),
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.label_arrival_time),
                style = MaterialTheme.typography.labelLarge,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 0.dp, top = 0.dp, bottom = 0.dp),
                text = formatIsoDate(arrivalTime),
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.label_boarding_time),
                style = MaterialTheme.typography.labelLarge,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 0.dp, top = 0.dp, bottom = 0.dp),
                text = formatIsoDate(boardingTime),
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.label_gate),
                style = MaterialTheme.typography.labelLarge,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 0.dp, top = 0.dp, bottom = 0.dp),
                text = gate,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

//@Preview
//@Composable
//fun CheckInScreenPreview() {
//    BookCabinTheme {
//        CheckInScreen(null, onCheckSuccess = {} as (String) -> Unit)
//    }
//}
