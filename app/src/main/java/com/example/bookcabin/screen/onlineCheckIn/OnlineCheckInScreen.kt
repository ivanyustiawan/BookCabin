package com.example.bookcabin.screen.onlineCheckIn

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookcabin.R
import com.example.bookcabin.ui.theme.BookCabinTheme
import uiState.AppUiState

@Composable
fun OnlineCheckInScreen(
    viewModel: OnlineCheckInViewModel = hiltViewModel(),
    onContinueClick: (String, String) -> Unit
) {
    val view = LocalView.current
    val window = (view.context as? Activity)?.window

    if (window != null) {
        SideEffect {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    val tokenState by viewModel.tokenState.collectAsState()
    val context = LocalContext.current

    val pnr = rememberSaveable { mutableStateOf("") }
    val lastName = rememberSaveable { mutableStateOf("") }

    when (tokenState) {
        is AppUiState.Idle -> {}
        is AppUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is AppUiState.Success -> {
            OnlineCheckInView(context, pnr, lastName, onContinueClick)
        }

        is AppUiState.Error -> {
            Toast.makeText(
                context,
                (tokenState as AppUiState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@Composable
fun OnlineCheckInView(
    context: Context,
    pnr: MutableState<String>,
    lastName: MutableState<String>,
    onContinueClick: (String, String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.secondary,
        bottomBar = {
            Button(
                onClick = {
                    if (isFieldFilled(
                            context = context,
                            pnr = pnr.value,
                            lastName = lastName.value
                        )
                    ) {
                        onContinueClick.invoke(pnr.value, lastName.value)
                    }
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 64.dp)
                    .height(48.dp)
            ) {
                Text(text = stringResource(id = R.string.button_continue))
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
                    text = stringResource(id = R.string.title_online_check_in),
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Spacer(Modifier.height(48.dp))

            Text(
                text = stringResource(id = R.string.label_pnr),
                style = MaterialTheme.typography.labelLarge,
            )

            Spacer(Modifier.height(8.dp))

            TextField(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
                textStyle = MaterialTheme.typography.bodyLarge,
                value = pnr.value,
                onValueChange = { pnr.value = it.uppercase() },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Ascii,
                    capitalization = KeyboardCapitalization.Characters
                ),
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.label_last_name),
                style = MaterialTheme.typography.labelLarge,
            )

            Spacer(Modifier.height(8.dp))

            TextField(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
                textStyle = MaterialTheme.typography.bodyLarge,
                value = lastName.value,
                onValueChange = { lastName.value = it },
                singleLine = true,
            )
        }
    }
}

fun isFieldFilled(
    context: Context,
    pnr: String,
    lastName: String,
): Boolean {
    if (pnr.isEmpty() || lastName.isEmpty()) {
        Toast.makeText(
            context,
            "Please fill all fields",
            Toast.LENGTH_SHORT
        ).show()
        return false
    }
    return true
}

@Preview
@Composable
fun OnlineCheckInScreenPreview() {
    BookCabinTheme {
        OnlineCheckInScreen(onContinueClick = {} as (String, String) -> Unit)
    }
}

