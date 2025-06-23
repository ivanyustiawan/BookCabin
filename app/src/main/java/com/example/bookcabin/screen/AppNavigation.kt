package com.example.bookcabin.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookcabin.screen.boardingPass.BoardingPassScreen
import com.example.bookcabin.screen.checkIn.CheckInScreen
import com.example.bookcabin.screen.onlineCheckIn.OnlineCheckInScreen
import com.example.bookcabin.screen.passengerDetails.PassengerDetailsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ONLINE_CHECK_IN.label) {
        composable(Screen.ONLINE_CHECK_IN.label) {
            OnlineCheckInScreen() { pnr, lastName ->
                navController.navigate("${Screen.PASSENGER_DETAILS.label}/$pnr/$lastName")
            }
        }

        composable(
            "${Screen.PASSENGER_DETAILS.label}/{pnr}/{lastName}",
            arguments = listOf(
                navArgument("pnr") { type = NavType.StringType },
                navArgument("lastName") { type = NavType.StringType })
        ) { backStackEntry ->
            val pnr = backStackEntry.arguments?.getString("pnr").orEmpty()
            val lastName = backStackEntry.arguments?.getString("lastName").orEmpty()
            PassengerDetailsScreen(pnr = pnr, lastName = lastName) { passengerId ->
                navController.navigate("${Screen.CHECK_IN.label}/$passengerId")
            }
        }

        composable(
            "${Screen.CHECK_IN.label}/{passengerId}",
            arguments = listOf(navArgument("passengerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val passengerId = backStackEntry.arguments?.getString("passengerId").orEmpty()
            CheckInScreen(passengerId) { flightId ->
                navController.navigate("${Screen.BOARDING_PASS.label}/$flightId")
            }
        }

        composable(
            "${Screen.BOARDING_PASS.label}/{flightId}",
            arguments = listOf(navArgument("flightId") { type = NavType.StringType })
        ) { backStackEntry ->
            val flightId = backStackEntry.arguments?.getString("flightId").orEmpty()
            BoardingPassScreen(flightId)
        }
    }
}

enum class Screen(val label: String) {
    ONLINE_CHECK_IN("onlineCheckIn"),
    PASSENGER_DETAILS("passengerDetails"),
    CHECK_IN("checkIn"),
    BOARDING_PASS("boardingPass"),
}
