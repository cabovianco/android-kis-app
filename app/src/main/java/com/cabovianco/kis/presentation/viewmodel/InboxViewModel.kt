package com.cabovianco.kis.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabovianco.kis.domain.model.SecretItem
import com.cabovianco.kis.domain.usecase.DeleteSecretUseCase
import com.cabovianco.kis.domain.usecase.GetAllSecretsUseCase
import com.cabovianco.kis.presentation.state.InboxUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InboxViewModel @Inject constructor(
    getAllSecretsUseCase: GetAllSecretsUseCase,
    private val deleteSecretUseCase: DeleteSecretUseCase
) : ViewModel() {
    val uiState: StateFlow<InboxUiState> = getAllSecretsUseCase()
        .map {
            if (it.isSuccess) {
                InboxUiState.Success(it.getOrNull() ?: emptyList())
            } else {
                InboxUiState.Error(
                    message = it.exceptionOrNull()?.message ?: "An error occurred"
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = InboxUiState.Loading
        )

    fun onCloseForever(secret: SecretItem) {
        viewModelScope.launch {
            deleteSecretUseCase(secret)
        }
    }
}
