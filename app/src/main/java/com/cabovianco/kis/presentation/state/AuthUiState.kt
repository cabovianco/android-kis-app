package com.cabovianco.kis.presentation.state

data class AuthUiState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val authState: AuthState = AuthState.Idle
)

sealed interface AuthState {
    object Idle : AuthState
    object Loading : AuthState
    object Success : AuthState
    data class Error(val message: String) : AuthState
}
