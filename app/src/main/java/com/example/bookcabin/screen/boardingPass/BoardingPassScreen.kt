package com.example.bookcabin.screen.boardingPass

import android.app.Activity
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookcabin.R
import com.example.bookcabin.ui.theme.BookCabinTheme
import com.example.bookcabin.ui.theme.CreamTan
import com.example.bookcabin.ui.theme.DarkBlue
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import model.PassengerBoardingPass
import uiState.AppUiState

@Composable
fun BoardingPassScreen(
    flightId: String,
    viewModel: BoardingPassViewModel = hiltViewModel(),
) {
    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val context = LocalContext.current

    val boardingPassState by viewModel.boardingPassInState.collectAsState()

    if (window != null) {
        SideEffect {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    LaunchedEffect(flightId) {
        viewModel.getBoardingPass(flightId)
    }

    when (boardingPassState) {
        is AppUiState.Idle -> {}
        is AppUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is AppUiState.Success -> {
            val boardingPass =
                (boardingPassState as AppUiState.Success<PassengerBoardingPass>).data
            BoardingPassView(boardingPass)
        }

        is AppUiState.Error -> {
            Toast.makeText(
                context,
                "Error: ${(boardingPassState as AppUiState.Error).message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@Composable
fun BoardingPassView(passengerBoardingPass: PassengerBoardingPass) {

    val name = passengerBoardingPass.boardingPasses[0].boardingPass.personName.first + " " + passengerBoardingPass.boardingPasses[0].boardingPass.personName.last
    val airlane = passengerBoardingPass.boardingPasses[0].boardingPass.flightDetail.airline + " " + passengerBoardingPass.boardingPasses[0].boardingPass.flightDetail.flightNumber
    val fareInfo =
        passengerBoardingPass.boardingPasses[0].boardingPass.fareInfo.bookingClass + "" +
                passengerBoardingPass.boardingPasses[0].boardingPass.group + "" + passengerBoardingPass.boardingPasses[0].boardingPass.zone
    val seat = passengerBoardingPass.boardingPasses[0].boardingPass.seat.value
    val departureAirport = passengerBoardingPass.boardingPasses[0].boardingPass.flightDetail.departureAirport
    val arrivalAirport = passengerBoardingPass.boardingPasses[0].boardingPass.flightDetail.arrivalAirport
    val barcode = passengerBoardingPass.boardingPasses[0].boardingPass.barCode

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.tertiary,
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(144.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(id = R.string.title_boarding_pass),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CreamTan),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(96.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = airlane,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = stringResource(id = R.string.label_seat),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = fareInfo,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = seat,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 0.dp)
            ) {
                DashedDivider()
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CreamTan),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = departureAirport,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = arrivalAirport,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    BarcodeImage(
                        content = barcode,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 16.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun DashedDivider(
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 1.dp,
    dashLength: Float = 10f,
    gapLength: Float = 10f
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(strokeWidth)
    ) {
        val canvasWidth = size.width

        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(canvasWidth, 0f),
            strokeWidth = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
        )
    }
}

@Composable
fun BarcodeImage(
    content: String,
    modifier: Modifier = Modifier,
    widthDp: Dp = 900.dp,
    heightDp: Dp = 300.dp
) {

    val density = LocalDensity.current
    val widthPx = with(density) { widthDp.roundToPx() }
    val heightPx = with(density) { heightDp.roundToPx() }

    val barcodeBitmap = remember(content, widthPx, heightPx) {
        generateBarcodeBitmap(content, widthPx, heightPx)
    }

    Image(
        bitmap = barcodeBitmap.asImageBitmap(),
        contentDescription = "Barcode",
        modifier = modifier
    )
}

fun generateBarcodeBitmap(
    content: String,
    width: Int,
    height: Int
): Bitmap {
    val bitMatrix: BitMatrix = MultiFormatWriter().encode(
        content,
        BarcodeFormat.CODE_128,
        width,
        height
    )

    val bitmap = createBitmap(width, height, Bitmap.Config.RGB_565)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap[x, y] =
                if (bitMatrix[x, y]) DarkBlue.toArgb() else CreamTan.toArgb()
        }
    }
    return bitmap
}

@Preview
@Composable
fun BoardingPassScreenPreview() {
    BookCabinTheme {
        BoardingPassScreen("")
    }
}