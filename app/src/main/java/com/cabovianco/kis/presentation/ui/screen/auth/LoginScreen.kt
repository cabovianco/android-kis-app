package com.cabovianco.kis.presentation.ui.screen.auth

import androidx.compose.foundation.layout.Arrangement
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

@Composable
fun LoginScreen(onRegisterClick: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            AuthFooter(onSignInClick = {}, onRegisterClick = onRegisterClick)
        }
    ) {
        LoginContent(
            email = "",
            password = "",
            onEmailChange = {},
            onPasswordChange = {},
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun LoginContent(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
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
            password = password,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange
        )

        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.login_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = stringResource(R.string.login_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun Form(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
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
        PasswordTextField(password = password, onPasswordChange = onPasswordChange)
    }
}

@Composable
private fun AuthFooter(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .windowInsetsPadding(WindowInsets.navigationBars),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignInButton(onClick = onSignInClick)
        RegisterPrompt(onClick = onRegisterClick)
    }
}

@Composable
private fun SignInButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    AppPrimaryButton(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        text = stringResource(R.string.login_sign_in_button),
        onClick = onClick
    )
}

@Composable
private fun RegisterPrompt(onClick: () -> Unit, modifier: Modifier = Modifier) {
    AppTextButton(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        text = stringResource(R.string.login_register_prompt),
        onClick = onClick
    )
}
