package com.cabovianco.kis.presentation.state

data class ComposeUiState(
    val username: String = "",
    val content: String = "",
    val composeState: ComposeState = ComposeState.Idle
)

sealed interface ComposeState {
    object Idle : ComposeState
    object Sending : ComposeState
    object Success : ComposeState
    data class Error(val message: String) : ComposeState
}
