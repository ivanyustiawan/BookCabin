package com.example.bookcabin.screen.boardingPass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import model.PassengerBoardingPass
import uiState.AppUiState
import usecase.GetPassengerUseCase
import javax.inject.Inject

@HiltViewModel
class BoardingPassViewModel @Inject constructor(
    private val getPassengerUseCase: GetPassengerUseCase
) : ViewModel() {

    private val _boardingPassInState =
        MutableStateFlow<AppUiState<PassengerBoardingPass>>(AppUiState.Idle)
    val boardingPassInState = _boardingPassInState.asStateFlow()

    private var isLoading = false

    fun getBoardingPass(flightId: String) {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            getPassengerUseCase.getPassengerBoardingPass(flightId)
                .onStart {
                    _boardingPassInState.value = AppUiState.Loading
                }
                .catch { e ->
                    _boardingPassInState.value =
                        AppUiState.Error(e.localizedMessage ?: "Unexpected Error")
                    isLoading = false
                }
                .collect { resBoardingPass ->
                    _boardingPassInState.value = AppUiState.Success(resBoardingPass)
                    isLoading = false
                }
        }
    }
}