package com.cabovianco.kis.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cabovianco.kis.presentation.event.AuthUiEvent
import com.cabovianco.kis.presentation.ui.screen.ComposeScreen
import com.cabovianco.kis.presentation.ui.screen.InboxScreen
import com.cabovianco.kis.presentation.ui.screen.SettingsScreen
import com.cabovianco.kis.presentation.ui.screen.auth.LoginScreen
import com.cabovianco.kis.presentation.ui.screen.auth.RegisterScreen
import com.cabovianco.kis.presentation.viewmodel.ComposeViewModel
import com.cabovianco.kis.presentation.viewmodel.InboxViewModel
import com.cabovianco.kis.presentation.viewmodel.LoginViewModel
import com.cabovianco.kis.presentation.viewmodel.RegisterViewModel
import com.cabovianco.kis.presentation.viewmodel.RootViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val rootViewModel = hiltViewModel<RootViewModel>()
    val startDestination by rootViewModel.startDestination.collectAsState()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable<Screen.Auth.Login> {
            val viewModel = hiltViewModel<LoginViewModel>()

            LaunchedEffect(Unit) { viewModel.uiEvent.collect { event -> event.handle(navController) } }

            LoginScreen(
                onRegisterClick = { navController.navigate(Screen.Auth.Register) },
                viewModel = viewModel
            )
        }

        composable<Screen.Auth.Register> {
            val viewModel = hiltViewModel<RegisterViewModel>()

            LaunchedEffect(Unit) { viewModel.uiEvent.collect { event -> event.handle(navController) } }

            RegisterScreen(
                onLoginClick = { navController.navigateUp() },
                viewModel = viewModel
            )
        }

        composable<Screen.Inbox> {
            val viewModel = hiltViewModel<InboxViewModel>()

            InboxScreen(
                onSettingsActionClick = { navController.navigate(Screen.Settings) },
                onComposeSecretFloatingActionClick = { navController.navigate(Screen.Compose) },
                viewModel = viewModel
            )
        }

        composable<Screen.Compose> {
            val viewModel = hiltViewModel<ComposeViewModel>()

            ComposeScreen(
                onNavBack = { navController.navigateUp() },
                viewModel = viewModel
            )
        }

        composable<Screen.Settings> {
            SettingsScreen(
                onNavBack = { navController.navigateUp() }
            )
        }
    }
}

private fun AuthUiEvent.handle(navController: androidx.navigation.NavController) {
    when (this) {
        is AuthUiEvent.SignIn -> {
            navController.navigate(Screen.Inbox)
        }

        is AuthUiEvent.SignUp -> {}

        is AuthUiEvent.SignOut -> {
            navController.navigate(Screen.Auth.Login)
        }
    }
}
