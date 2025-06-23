package com.example.bookcabin.screen.passengerDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import model.PassengerDetails
import uiState.AppUiState
import usecase.GetPassengerUseCase
import javax.inject.Inject

@HiltViewModel
class PassengerDetailsViewModel @Inject constructor(
    private val getPassengerUseCase: GetPassengerUseCase
) : ViewModel() {

    private val _passengerDetailsState =
        MutableStateFlow<AppUiState<PassengerDetails>>(AppUiState.Idle)
    val passengerDetailsState = _passengerDetailsState.asStateFlow()

    private var passengerDetails by mutableStateOf<PassengerDetails?>(null)

    private val _passengerUpdatesState =
        MutableStateFlow<AppUiState<PassengerDetails>>(AppUiState.Idle)
    val passengerUpdatesState = _passengerUpdatesState.asStateFlow()

    private var isLoading = false

    fun getPassengerDetails(pnr: String, lastName: String) {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            getPassengerUseCase.getPassengerDetails(pnr, lastName)
                .onStart {
                    _passengerDetailsState.value = AppUiState.Loading
                }
                .catch { e ->
                    _passengerDetailsState.value =
                        AppUiState.Error(e.localizedMessage ?: "Unexpected Error")
                    isLoading = false
                }
                .collect { resPassengerDetails ->
                    passengerDetails = resPassengerDetails
                    _passengerDetailsState.value = AppUiState.Success(resPassengerDetails)
                    isLoading = false
                }
        }
    }

    fun getPassengerUpdate() {
        if (isLoading) return
        isLoading = true

        passengerDetails?.let {
            viewModelScope.launch {
                getPassengerUseCase.getPassengerUpdate(
                    it.reservation.passengers.passenger[0].weightCategory,
                    it.reservation.passengers.passenger[0].id
                )
                    .onStart {
                        _passengerUpdatesState.value = AppUiState.Loading
                    }
                    .catch { e ->
                        _passengerUpdatesState.value =
                            AppUiState.Error(e.localizedMessage ?: "Unexpected Error")
                        isLoading = false
                    }
                    .collect { results ->
                        _passengerUpdatesState.value = AppUiState.Success(it)
                        isLoading = false
                    }
            }
        }
    }

    fun setState(value: AppUiState<PassengerDetails>) {
        _passengerUpdatesState.value = value
    }

}