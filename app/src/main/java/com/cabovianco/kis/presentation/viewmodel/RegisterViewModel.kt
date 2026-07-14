package com.cabovianco.kis.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabovianco.kis.domain.usecase.auth.SignInUseCase
import com.cabovianco.kis.domain.usecase.auth.SignUpUseCase
import com.cabovianco.kis.presentation.event.AuthUiEvent
import com.cabovianco.kis.presentation.state.AuthState
import com.cabovianco.kis.presentation.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> get() = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<AuthUiEvent> = MutableSharedFlow()
    val uiEvent: MutableSharedFlow<AuthUiEvent> get() = _uiEvent

    fun signUp() {
        viewModelScope.launch {
            _uiState.update { it.copy(authState = AuthState.Loading) }

            signUpUseCase(_uiState.value.email, _uiState.value.password)
                .onSuccess { signIn() }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            authState = AuthState.Error(
                                error.message ?: "Failed to sign up. Please try again."
                            )
                        )
                    }
                }
        }
    }

    private suspend fun signIn() {
        _uiState.update { it.copy(authState = AuthState.Loading) }

        signInUseCase(_uiState.value.email, _uiState.value.password)
            .onSuccess {
                _uiState.update { it.copy(authState = AuthState.Success) }
                _uiEvent.emit(AuthUiEvent.SignIn)
            }
            .onFailure { error ->
                _uiState.update {
                    it.copy(
                        authState = AuthState.Error(
                            error.message ?: "Failed to sign in. Please try again."
                        )
                    )
                }
            }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }
}
