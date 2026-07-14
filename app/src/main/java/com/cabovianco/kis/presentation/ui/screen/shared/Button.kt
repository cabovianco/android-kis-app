package com.cabovianco.kis.presentation.ui.screen.shared

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val AppButtonShape = RoundedCornerShape(16.dp)

@Composable
fun AppPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    AppButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.inverseSurface,
            contentColor = MaterialTheme.colorScheme.inverseOnSurface
        )
    )
}

@Composable
fun AppSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    AppButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    )
}

@Composable
private fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors()
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = AppButtonShape,
        colors = colors
    ) {
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun AppTextButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        shape = AppButtonShape,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )
    }
}
