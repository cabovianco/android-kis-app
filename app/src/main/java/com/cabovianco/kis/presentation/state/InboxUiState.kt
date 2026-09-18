package com.cabovianco.kis.presentation.state

import com.cabovianco.kis.domain.model.SecretItem

sealed interface InboxUiState {
    object Loading : InboxUiState
    data class Success(val items: List<SecretItem>) : InboxUiState
    data class Error(val message: String) : InboxUiState
}
