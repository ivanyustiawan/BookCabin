package com.example.bookcabin.screen.checkIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import uiState.AppUiState
import usecase.GetPassengerUseCase
import javax.inject.Inject

@HiltViewModel
class CheckInViewModel @Inject constructor(
    private val getPassengerUseCase: GetPassengerUseCase
) : ViewModel() {

    private val _checkInState = MutableStateFlow<AppUiState<String>>(AppUiState.Idle)
    val checkInState = _checkInState.asStateFlow()

    private var isLoading = false

    fun getPassengerCheckIn(passengerIds: String) {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            getPassengerUseCase.getPassengerCheckIn(passengerIds)
                .onStart {
                    _checkInState.value = AppUiState.Loading
                }
                .catch { e ->
                    _checkInState.value = AppUiState.Error(e.localizedMessage ?: "Unexpected Error")
                    isLoading = false
                }
                .collect { passengerCheckIn ->
                    _checkInState.value =
                        AppUiState.Success(passengerCheckIn.results[0].passengerFlightRef)
                    isLoading = false
                }
        }

    }

    fun setState(value: AppUiState<String>){
        _checkInState.value = value
    }

}