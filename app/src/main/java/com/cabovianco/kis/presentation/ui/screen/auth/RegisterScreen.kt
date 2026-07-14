package com.cabovianco.kis.presentation.ui.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cabovianco.kis.R
import com.cabovianco.kis.presentation.ui.screen.auth.shared.AppIcon
import com.cabovianco.kis.presentation.ui.screen.auth.shared.EmailTextField
import com.cabovianco.kis.presentation.ui.screen.auth.shared.PasswordTextField
import com.cabovianco.kis.presentation.ui.screen.shared.AppPrimaryButton
import com.cabovianco.kis.presentation.ui.screen.shared.AppTextButton
import com.cabovianco.kis.presentation.ui.screen.shared.UsernameTextField
import com.cabovianco.kis.presentation.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit,
    viewModel: RegisterViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            AuthFooter(onSignUpClick = { viewModel.signUp() }, onLoginClick = onLoginClick)
        }
    ) {
        RegisterContent(
            email = uiState.email,
            username = uiState.username,
            password = uiState.password,
            onEmailChange = { email -> viewModel.onEmailChange(email) },
            onUsernameChange = { username -> viewModel.onUsernameChange(username) },
            onPasswordChange = { password -> viewModel.onPasswordChange(password) },
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun RegisterContent(
    email: String,
    username: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        AppIcon()
        Header()

        Spacer(modifier = Modifier.weight(0.25f))

        Form(
            email = email,
            username = username,
            password = password,
            onEmailChange = onEmailChange,
            onUsernameChange = onUsernameChange,
            onPasswordChange = onPasswordChange
        )

        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(R.string.register_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun Form(
    email: String,
    username: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailTextField(email = email, onEmailChange = onEmailChange)
        UsernameTextField(username = username, onUsernameChange = onUsernameChange)
        PasswordTextField(password = password, onPasswordChange = onPasswordChange)
    }
}

@Composable
private fun AuthFooter(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .windowInsetsPadding(WindowInsets.navigationBars),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignUpButton(onClick = onSignUpClick)
        LoginPrompt(onClick = onLoginClick)
    }
}

@Composable
private fun SignUpButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    AppPrimaryButton(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        text = stringResource(R.string.register_sign_up_button),
        onClick = onClick
    )
}

@Composable
private fun LoginPrompt(onClick: () -> Unit, modifier: Modifier = Modifier) {
    AppTextButton(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        text = stringResource(R.string.register_login_prompt),
        onClick = onClick
    )
}
