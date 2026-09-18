package com.cabovianco.kis.presentation.ui.screen.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val AppMenuShape = RoundedCornerShape(16.dp)

data class Menu(
    val title: String,
    val items: List<MenuItem>
)

data class MenuItem(
    val title: String,
    val onClick: () -> Unit,
    val leadingContent: @Composable () -> Unit,
    val trailingContent: (@Composable () -> Unit)? = null,
    val clickable: Boolean = true
)

@Composable
fun AppMenu(menu: Menu, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppMenuTitle(title = menu.title)
        AppMenuItems(items = menu.items)
    }
}

@Composable
private fun AppMenuTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun AppMenuItems(
    items: List<MenuItem>,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = AppMenuShape
    ) {
        Column {
            items.forEachIndexed { index, item ->
                AppMenuItem(item = item)

                if (index < items.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                    )
                }
            }
        }
    }
}

@Composable
private fun AppMenuItem(
    item: MenuItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(enabled = item.clickable) { item.onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(22.dp)) { item.leadingContent() }

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            text = item.title,
            style = MaterialTheme.typography.bodyLarge
        )

        Box(modifier = Modifier.size(22.dp)) { item.trailingContent?.invoke() }
    }
}
