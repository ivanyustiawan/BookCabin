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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookcabin.R
import com.example.bookcabin.ui.theme.BookCabinTheme
import uiState.AppUiState

@Composable
fun CheckInScreen(
    name: String,
    info: String,
    passengerIds: String,
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

    CheckInView(viewModel, passengerIds, name, info)

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
fun CheckInView(viewModel: CheckInViewModel, passengerIds: String, name: String, info: String) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.secondary,
        bottomBar = {
            Button(
                onClick = {
                    viewModel.getPassengerCheckIn(passengerIds)
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
        }
    }
}

@Preview
@Composable
fun CheckInScreenPreview() {
    BookCabinTheme {
        CheckInScreen("", "", "") {}
    }
}
