package com.cabovianco.kis.presentation.event

sealed interface AuthUiEvent {
    object SignIn : AuthUiEvent
    object SignUp : AuthUiEvent
    object SignOut : AuthUiEvent
}
