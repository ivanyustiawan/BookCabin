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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookcabin.R
import com.example.bookcabin.ui.theme.BookCabinTheme
import com.example.bookcabin.ui.theme.Pink80
import model.Gender
import model.PassengerDetails
import uiState.AppUiState

@Composable
fun PassengerDetailsScreen(
    pnr: String,
    lastName: String,
    viewModel: PassengerDetailsViewModel = hiltViewModel(),
    onSaveClick: (passengerDetails: PassengerDetails) -> Unit
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

            PassengerDetailsView(
                passengerDetails,
                viewModel
            )
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
            val passengerDetails =
                (passengerUpdateState as AppUiState.Success<PassengerDetails>).data
            val passengerId = passengerDetails.reservation.passengers.passenger[0].id

            if (passengerId.isNotEmpty()) {
                onSaveClick.invoke(passengerDetails)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerDetailsView(
    passengerDetails: PassengerDetails,
    viewModel: PassengerDetailsViewModel,
) {

    val passportField = remember { mutableStateOf("") }
    val isErrorPassport = remember { mutableStateOf(false) }
    val firstNameField = remember { mutableStateOf("") }
    val isErrorFirstName = remember { mutableStateOf(false) }
    val lastNameField = remember { mutableStateOf("") }
    val isErrorLastName = remember { mutableStateOf(false) }
    val genderList = Gender.entries.toList()
    val expanded = remember { mutableStateOf(false) }
    val selectedField = remember { mutableStateOf(genderList[0]) }
    val contactNameField = remember { mutableStateOf("") }
    val isErrorContactName = remember { mutableStateOf(false) }
    val expandedCountry = remember { mutableStateOf(false) }
    val listCountry = listOf<String>("IN", "US", "MY", "EN")
    val selectedCountryField = remember { mutableStateOf(listCountry[0]) }
    val contactNumberField = remember { mutableStateOf("") }
    val isErrorContactNumber = remember { mutableStateOf(false) }
    val relationshipField = remember { mutableStateOf("") }
    val isErrorRelationship = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.tertiary,
        bottomBar = {
            Surface(
                tonalElevation = 4.dp,
                shadowElevation = 4.dp,
                color = MaterialTheme.colorScheme.tertiary
            ) {
                Button(
                    enabled = !isErrorPassport.value
                            && !isErrorFirstName.value
                            && !isErrorLastName.value
                            && passportField.value.isNotEmpty()
                            && firstNameField.value.isNotEmpty()
                            && lastNameField.value.isNotEmpty()
                            && contactNameField.value.isNotEmpty()
                            && !isErrorContactName.value
                            && contactNumberField.value.isNotEmpty()
                            && !isErrorContactNumber.value
                            && relationshipField.value.isNotEmpty()
                            && !isErrorRelationship.value,
                    onClick = {
                        viewModel.getPassengerUpdate(
                            passportField.value,
                            firstNameField.value,
                            lastNameField.value,
                            selectedField.value,
                            contactNameField.value,
                            selectedCountryField.value,
                            contactNumberField.value,
                            relationshipField.value,
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .navigationBarsPadding()
                        .height(48.dp)
                ) {
                    Text(text = stringResource(id = R.string.button_save))
                }
            }

        }) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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

            AnimatedVisibility(
                visible = passportField.value.isEmpty()
                        && firstNameField.value.isEmpty()
                        && lastNameField.value.isEmpty()
            ) {
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
                value = passportField.value,
                onValueChange = {
                    if (it.length <= 9) passportField.value = it
                    isErrorPassport.value = !regex.matches(passportField.value)
                },
                singleLine = true,
            )
            AnimatedVisibility(visible = isErrorPassport.value) {
                Text(
                    "Passport Number must be letters and digits and 6 to 9 characters long",
                    color = Color.Red
                )
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.label_first_name),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(Modifier.height(8.dp))
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
                value = firstNameField.value,
                onValueChange = {
                    firstNameField.value = it.uppercase()
                    isErrorFirstName.value = firstNameField.value.isEmpty()
                },
                singleLine = true,
            )
            AnimatedVisibility(visible = isErrorFirstName.value) {
                Text(
                    "First name cannot be empty",
                    color = Color.Red
                )
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.label_last_name),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(Modifier.height(8.dp))
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
                value = lastNameField.value,
                onValueChange = {
                    lastNameField.value = it.uppercase()
                    isErrorLastName.value = lastNameField.value.isEmpty()
                },
                singleLine = true,
            )
            AnimatedVisibility(visible = isErrorLastName.value) {
                Text(
                    "Last name cannot be empty",
                    color = Color.Red
                )
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.label_gender),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = expanded.value,
                onExpandedChange = { expanded.value = !expanded.value }
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
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
                    value = selectedField.value.gender,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
                )

                ExposedDropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    genderList.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item.gender) },
                            onClick = {
                                selectedField.value = item
                                expanded.value = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.label_emergency_contact),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.label_contact_name),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(Modifier.height(8.dp))
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
                value = contactNameField.value,
                onValueChange = {
                    contactNameField.value = it.uppercase()
                    isErrorContactName.value = contactNameField.value.isEmpty()
                },
                singleLine = true,
            )
            AnimatedVisibility(visible = isErrorContactName.value) {
                Text(
                    "Contact name cannot be empty",
                    color = Color.Red
                )
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.label_contact_country),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = expandedCountry.value,
                onExpandedChange = { expandedCountry.value = !expandedCountry.value }
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
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
                    value = selectedCountryField.value,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCountry.value) },
                )

                ExposedDropdownMenu(
                    expanded = expandedCountry.value,
                    onDismissRequest = { expandedCountry.value = false }
                ) {
                    listCountry.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                selectedCountryField.value = item
                                expandedCountry.value = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.label_contact_number),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(Modifier.height(8.dp))
            val regexNumber = Regex("^[0-9]{9,13}$")
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
                value = contactNumberField.value,
                onValueChange = {
                    if (it.length <= 13) contactNumberField.value = it
                    isErrorContactNumber.value = !regexNumber.matches(contactNumberField.value)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
            )
            AnimatedVisibility(visible = isErrorContactNumber.value) {
                Text(
                    "Contact number cannot be empty and must be 9 to 13 digits long",
                    color = Color.Red
                )
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.label_contact_relationship),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(Modifier.height(8.dp))
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
                value = relationshipField.value,
                onValueChange = {
                    relationshipField.value = it
                    isErrorRelationship.value = relationshipField.value.isEmpty()
                },
                singleLine = true,
            )
            AnimatedVisibility(visible = isErrorRelationship.value) {
                Text(
                    "Contact relationship cannot be empty",
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
        PassengerDetailsScreen("", "", onSaveClick = {} as (PassengerDetails) -> Unit)
    }
}