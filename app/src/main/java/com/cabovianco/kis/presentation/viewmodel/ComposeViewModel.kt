package com.cabovianco.kis.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabovianco.kis.domain.model.SecretComposeItem
import com.cabovianco.kis.domain.usecase.SendSecretUseCase
import com.cabovianco.kis.presentation.state.ComposeState
import com.cabovianco.kis.presentation.state.ComposeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComposeViewModel @Inject constructor(
    private val sendSecretUseCase: SendSecretUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<ComposeUiState> = MutableStateFlow(ComposeUiState())
    val uiState: StateFlow<ComposeUiState> get() = _uiState.asStateFlow()

    fun composeAndSend() {
        compose()
            .onSuccess { secret ->
                viewModelScope.launch { send(secret) }
            }
            .onFailure { exception ->
                _uiState.update {
                    it.copy(
                        composeState = ComposeState.Error(
                            exception.message ?: "An error occurred"
                        )
                    )
                }
            }
    }

    private fun compose(): Result<SecretComposeItem> {
        if (_uiState.value.username.isBlank()) {
            return Result.failure(Exception("Username cannot be empty"))
        }

        if (_uiState.value.content.isBlank()) {
            return Result.failure(Exception("Content cannot be empty"))
        }

        return Result.success(
            SecretComposeItem(
                to = _uiState.value.username,
                content = _uiState.value.content
            )
        )
    }

    private suspend fun send(secret: SecretComposeItem) {
        _uiState.update { it.copy(composeState = ComposeState.Sending) }

        sendSecretUseCase(secret)
            .onSuccess { _uiState.update { it.copy(composeState = ComposeState.Success) } }
            .onFailure { exception ->
                _uiState.update {
                    it.copy(
                        composeState = ComposeState.Error(
                            exception.message ?: "An error occurred"
                        )
                    )
                }
            }
    }

    fun onUsernameChange(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun onContentChange(content: String) {
        _uiState.value = _uiState.value.copy(content = content)
    }
}
