package com.cabovianco.kis.presentation.ui.screen.shared

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val AppTextFieldShape = RoundedCornerShape(16.dp)

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyLarge,
        placeholder = { Text(text = placeholder) },
        leadingIcon = leadingIcon,
        singleLine = true,
        shape = AppTextFieldShape,
        colors = MaterialTheme.colorScheme.run {
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primary,
                unfocusedBorderColor = primary,
                focusedLeadingIconColor = primary,
                unfocusedLeadingIconColor = primary,
                focusedPlaceholderColor = primary,
                unfocusedPlaceholderColor = primary
            )
        }
    )
}
