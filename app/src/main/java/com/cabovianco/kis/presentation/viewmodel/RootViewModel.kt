package com.cabovianco.kis.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabovianco.kis.domain.usecase.SignOutUseCase
import com.cabovianco.kis.domain.usecase.auth.IsLoggedInUseCase
import com.cabovianco.kis.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    isLoggedInUseCase: IsLoggedInUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {
    private val _startDestination: MutableStateFlow<Screen> = MutableStateFlow(Screen.Auth.Login)
    val startDestination: StateFlow<Screen> get() = _startDestination.asStateFlow()

    init {
        if (isLoggedInUseCase()) _startDestination.update { Screen.Inbox }
    }

    fun signOut() {
        viewModelScope.launch { signOutUseCase() }
        _startDestination.update { Screen.Auth.Login }
    }
}
