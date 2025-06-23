package uiState

sealed class AppUiState<out T> {
    data object Idle : AppUiState<Nothing>()
    data object Loading : AppUiState<Nothing>()
    data class Success<out T>(val data: T) : AppUiState<T>()
    data class Error(val message: String) : AppUiState<Nothing>()
}
