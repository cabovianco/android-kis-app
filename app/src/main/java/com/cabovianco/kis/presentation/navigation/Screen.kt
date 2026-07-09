package com.cabovianco.kis.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    sealed interface Auth : Screen {
        @Serializable
        object Login : Auth

        @Serializable
        object Register : Auth
    }

    @Serializable
    object Inbox : Screen

    @Serializable
    object Compose : Screen
}
