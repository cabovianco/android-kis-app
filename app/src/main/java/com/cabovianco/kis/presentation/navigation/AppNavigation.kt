package com.cabovianco.kis.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cabovianco.kis.presentation.ui.screen.ComposeScreen
import com.cabovianco.kis.presentation.ui.screen.InboxScreen
import com.cabovianco.kis.presentation.ui.screen.SettingsScreen
import com.cabovianco.kis.presentation.ui.screen.auth.LoginScreen
import com.cabovianco.kis.presentation.ui.screen.auth.RegisterScreen
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
            LoginScreen(onRegisterClick = { navController.navigate(Screen.Auth.Register) })
        }

        composable<Screen.Auth.Register> {
            RegisterScreen(onLoginClick = { navController.navigateUp() })
        }

        composable<Screen.Inbox> {
            InboxScreen(
                onSettingsActionClick = { navController.navigate(Screen.Settings) },
                onComposeSecretFloatingActionClick = { navController.navigate(Screen.Compose) }
            )
        }

        composable<Screen.Compose> {
            ComposeScreen(
                onNavBack = { navController.navigateUp() },
                onComposeAndSendClick = { navController.navigateUp() }
            )
        }

        composable<Screen.Settings> {
            SettingsScreen(
                onNavBack = { navController.navigateUp() }
            )
        }
    }
}
