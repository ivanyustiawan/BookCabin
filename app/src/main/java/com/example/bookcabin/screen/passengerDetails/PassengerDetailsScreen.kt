package com.example.bookcabin.screen.passengerDetails

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookcabin.R
import com.example.bookcabin.ui.theme.BookCabinTheme
import com.example.bookcabin.ui.theme.Pink80
import model.PassengerDetails
import uiState.AppUiState

@Composable
fun PassengerDetailsScreen(
    pnr: String,
    lastName: String,
    viewModel: PassengerDetailsViewModel = hiltViewModel(),
    onSaveClick: (String) -> Unit
) {
    val view = LocalView.current
    val window = (view.context as? Activity)?.window

    if (window != null) {
        SideEffect {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    val passengerDetailsState by viewModel.passengerDetailsState.collectAsState()
    val passengerUpdateState by viewModel.passengerUpdatesState.collectAsState()

    val context = LocalContext.current

    val passport = remember { mutableStateOf("") }
    val isError = remember { mutableStateOf(false) }

    LaunchedEffect(pnr, lastName) {
        viewModel.getPassengerDetails(pnr = pnr, lastName = lastName)
    }

    when (passengerDetailsState) {
        is AppUiState.Idle -> {}
        is AppUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is AppUiState.Success -> {
            val passengerDetails =
                (passengerDetailsState as AppUiState.Success<PassengerDetails>).data

            PassengerDetailsView(passport, isError, passengerDetails, viewModel)
        }

        is AppUiState.Error -> {
            Toast.makeText(
                context,
                "Error: ${(passengerDetailsState as AppUiState.Error).message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    when (passengerUpdateState) {
        is AppUiState.Idle -> {}
        is AppUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is AppUiState.Success -> {
            val passengerId = (passengerUpdateState as AppUiState.Success<String>).data
            if (passengerId.isNotEmpty()) {
                onSaveClick.invoke(passengerId)
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
                "Error: ${(passengerUpdateState as AppUiState.Error).message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@Composable
fun PassengerDetailsView(
    passport: MutableState<String>,
    isError: MutableState<Boolean>,
    passengerDetails: PassengerDetails,
    viewModel: PassengerDetailsViewModel,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.tertiary,
        bottomBar = {
            Button(
                enabled = passport.value.isNotEmpty() && !isError.value,
                onClick = {
                    viewModel.getPassengerUpdate()
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 64.dp)
                    .height(48.dp)
            ) {
                Text(text = stringResource(id = R.string.button_save))
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
                    text = stringResource(id = R.string.title_passenger_details),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "${passengerDetails.reservation.passengers.passenger[0].personName.first} ${passengerDetails.reservation.passengers.passenger[0].personName.last}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary
            )

            val airlane =
                passengerDetails.reservation.passengers.passenger[0].passengerSegments.passengerSegment[0].passengerFlight[0].boardingPass.flightDetail.airline
            val flightNumber =
                passengerDetails.reservation.passengers.passenger[0].passengerSegments.passengerSegment[0].passengerFlight[0].boardingPass.flightDetail.flightNumber
            val departureAirport =
                passengerDetails.reservation.passengers.passenger[0].passengerSegments.passengerSegment[0].passengerFlight[0].boardingPass.displayData.departureAirportName
            val arrivalAirport =
                passengerDetails.reservation.passengers.passenger[0].passengerSegments.passengerSegment[0].passengerFlight[0].boardingPass.displayData.arrivalAirportName

            Text(
                text = "$airlane $flightNumber  $departureAirport to $arrivalAirport",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(Modifier.height(24.dp))

            AnimatedVisibility(visible = passport.value.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp), contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = stringResource(id = R.string.label_passport_information),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.label_enter_document),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.label_passport_number),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )

            Spacer(Modifier.height(8.dp))

            val regex = Regex("^[A-Za-z0-9]{6,9}$")
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(width = 2.dp, color = Pink80, shape = RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
                textStyle = MaterialTheme.typography.bodyLarge,
                value = passport.value,
                onValueChange = {
                    if (it.length <= 9) passport.value = it
                    isError.value = !regex.matches(passport.value)
                },
                singleLine = true,
            )

            AnimatedVisibility(visible = isError.value) {
                Text(
                    "Passport Number must be letters and digits and 6 to 9 characters long",
                    color = Color.Red
                )
            }
        }
    }
}

@Preview
@Composable
fun PassengerDetailsScreenPreview() {
    BookCabinTheme {
        PassengerDetailsScreen("", "") {}
    }
}