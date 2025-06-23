package com.example.bookcabin.screen.onlineCheckIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import uiState.AppUiState
import usecase.GetAuthTokenUseCase
import javax.inject.Inject

@HiltViewModel
class OnlineCheckInViewModel @Inject constructor(
    private val getAuthTokenUseCase: GetAuthTokenUseCase
) : ViewModel() {

    private val _tokenState = MutableStateFlow<AppUiState<Boolean>>(AppUiState.Idle)
    val tokenState = _tokenState.asStateFlow()

    private var isLoading = false

    init {
        getAuthToken()
    }

    fun getAuthToken() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            getAuthTokenUseCase()
                .onStart {
                    _tokenState.value = AppUiState.Loading
                }
                .catch { e ->
                    _tokenState.value = AppUiState.Error(e.localizedMessage ?: "Unexpected Error")
                    isLoading = false
                }
                .collect { status ->
                    _tokenState.value = AppUiState.Success(status)
                    isLoading = false
                }
        }
    }
}