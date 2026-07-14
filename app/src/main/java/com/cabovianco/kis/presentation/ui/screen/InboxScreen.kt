package com.cabovianco.kis.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cabovianco.kis.R
import com.cabovianco.kis.domain.model.SecretItem
import com.cabovianco.kis.presentation.ui.screen.shared.AppBottomSheet
import com.cabovianco.kis.presentation.ui.screen.shared.AppPrimaryButton
import com.cabovianco.kis.presentation.ui.screen.shared.AppSecondaryButton

@Composable
fun InboxScreen(
    onSettingsActionClick: () -> Unit,
    onComposeSecretFloatingActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar(onSettingsActionClick = onSettingsActionClick) },
        floatingActionButton = { ComposeSecretFloatingAction(onClick = onComposeSecretFloatingActionClick) }
    ) {
        InboxContent(
            items = listOf(
                SecretItem(content = "Secret 1", from = "Person 1"),
                SecretItem(content = "Secret 2", from = "Person 2"),
                SecretItem(content = "Secret 3", from = "Person 3"),
            ),
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        )
    }
}

@Composable
private fun InboxContent(items: List<SecretItem>, modifier: Modifier = Modifier) {
    var isSecretPreviewOpen by rememberSaveable { mutableStateOf(false) }
    var isSecretOpen by rememberSaveable { mutableStateOf(false) }
    var selectedSecret by rememberSaveable { mutableStateOf<SecretItem?>(null) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically)
    ) {
        InboxSectionTitle()
        InboxSectionGrid(
            items = items,
            onItemClick = {
                selectedSecret = it
                isSecretPreviewOpen = true
            }
        )
    }

    selectedSecret?.let {
        InboxBottomSheet(
            secretItem = it,
            isPreviewOpen = isSecretPreviewOpen,
            onDismissRequest = {
                isSecretPreviewOpen = false
                isSecretOpen = false
                selectedSecret = null
            },
            onOpenSecretClick = {
                isSecretOpen = true
                isSecretPreviewOpen = false
            }
        )
    }
}

@Composable
private fun InboxSectionTitle(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.inbox_section_title),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun InboxSectionGrid(
    items: List<SecretItem>,
    onItemClick: (SecretItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { SecretItemCard(item = it, onClick = { onItemClick(it) }) }
    }
}

@Composable
private fun SecretItemCard(item: SecretItem, onClick: () -> Unit, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier.size(164.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SecretStatusIcon()
            SecretAuthor(name = item.from)
        }
    }
}

@Composable
private fun SecretStatusIcon(modifier: Modifier = Modifier, closed: Boolean = true) {
    FilledTonalIconButton(
        modifier = modifier,
        onClick = {},
        enabled = false,
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            painter = painterResource(if (closed) R.drawable.secret_closed else R.drawable.secret_open),
            contentDescription = null
        )
    }
}

@Composable
private fun SecretAuthor(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = name,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 1
    )
}

@Composable
private fun InboxBottomSheet(
    secretItem: SecretItem,
    isPreviewOpen: Boolean,
    onDismissRequest: () -> Unit,
    onOpenSecretClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SecretStatusIcon(closed = isPreviewOpen)
                SecretAuthor(name = secretItem.from)
            }
        }
    ) {
        if (isPreviewOpen)
            SecretPreview(
                onOpenSecretClick = onOpenSecretClick,
                onCancelClick = onDismissRequest
            ) else
            SecretOpened(
                secretItem = secretItem,
                onCloseSecretClick = onDismissRequest
            )
    }
}

@Composable
private fun SecretPreview(
    onOpenSecretClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.45f)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        SecretPreviewDescription()

        Spacer(modifier = Modifier.weight(1f))

        SecretPreviewOpenButton(onClick = onOpenSecretClick)
        SecretPreviewCancelButton(onClick = onCancelClick)

        Spacer(modifier = Modifier)
    }
}

@Composable
private fun SecretPreviewDescription(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(R.string.inbox_preview_description),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun SecretPreviewOpenButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    AppPrimaryButton(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        text = stringResource(R.string.inbox_open_secret_button),
        onClick = onClick
    )
}

@Composable
private fun SecretPreviewCancelButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    AppSecondaryButton(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        text = stringResource(R.string.inbox_cancel_button),
        onClick = onClick
    )
}

@Composable
private fun SecretOpened(
    secretItem: SecretItem,
    onCloseSecretClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.45f)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SecretContent(modifier = Modifier.weight(1f), text = secretItem.content)
        SecretCloseButton(onClick = onCloseSecretClick)

        Spacer(modifier = Modifier)
    }
}

@Composable
private fun SecretContent(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun SecretCloseButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    AppPrimaryButton(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        text = stringResource(R.string.inbox_close_button),
        onClick = onClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onSettingsActionClick: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.inbox_title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = { SettingsAction(onClick = onSettingsActionClick) }
    )
}

@Composable
private fun SettingsAction(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(R.drawable.settings),
            contentDescription = null
        )
    }
}

@Composable
private fun ComposeSecretFloatingAction(onClick: () -> Unit, modifier: Modifier = Modifier) {
    LargeFloatingActionButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(R.drawable.compose_secret),
            contentDescription = null
        )
    }
}
