package com.cabovianco.kis.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cabovianco.kis.R
import com.cabovianco.kis.presentation.ui.screen.shared.AppMenu
import com.cabovianco.kis.presentation.ui.screen.shared.Menu
import com.cabovianco.kis.presentation.ui.screen.shared.MenuItem

@Composable
fun SettingsScreen(
    onNavBack: () -> Unit,
    onLogOutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar(onNavBack = onNavBack) }
    ) {
        SettingsContent(
            isNotificationEnabled = true,
            onNotificationEnabledChange = {},
            onLogOutClick = onLogOutClick,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun SettingsContent(
    isNotificationEnabled: Boolean,
    onNotificationEnabledChange: (Boolean) -> Unit,
    onLogOutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AccountMenu(
            isNotificationEnabled = isNotificationEnabled,
            onNotificationEnabledChange = onNotificationEnabledChange,
            onLogOutClick = onLogOutClick
        )
    }
}

@Composable
private fun AccountMenu(
    isNotificationEnabled: Boolean,
    onNotificationEnabledChange: (Boolean) -> Unit,
    onLogOutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppMenu(
        modifier = modifier,
        menu = Menu(
            title = stringResource(R.string.settings_account_menu_title),
            items = listOf(
                notificationMenuOption(
                    isChecked = isNotificationEnabled,
                    onCheckedChange = onNotificationEnabledChange
                ),
                logoutMenuOption { onLogOutClick() },
                deleteAccountMenuOption { }
            )
        )
    )
}

@Composable
private fun notificationMenuOption(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
): MenuItem {
    return MenuItem(
        title = stringResource(R.string.settings_account_menu_notification_option),
        onClick = {},
        leadingContent = {
            Icon(
                painter = painterResource(R.drawable.ic_notifications),
                contentDescription = null
            )
        },
        trailingContent = {
            Switch(
                modifier = Modifier.padding(end = 32.dp),
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
        },
        clickable = false
    )
}

@Composable
private fun logoutMenuOption(onClick: () -> Unit): MenuItem {
    return MenuItem(
        title = stringResource(R.string.settings_account_menu_logout_option),
        onClick = onClick,
        leadingContent = {
            Icon(
                painter = painterResource(R.drawable.ic_logout),
                contentDescription = null
            )
        }
    )
}

@Composable
private fun deleteAccountMenuOption(onClick: () -> Unit): MenuItem {
    return MenuItem(
        title = stringResource(R.string.settings_account_menu_delete_option),
        onClick = onClick,
        leadingContent = {
            Icon(
                painter = painterResource(R.drawable.ic_delete),
                contentDescription = null
            )
        },
        clickable = false
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onNavBack: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.settings_title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavBack
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
        }
    )
}
