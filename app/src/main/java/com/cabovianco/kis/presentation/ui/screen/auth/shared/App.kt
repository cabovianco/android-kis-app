package com.cabovianco.kis.presentation.ui.screen.auth.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cabovianco.kis.R

@Composable
fun AppIcon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(128.dp),
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
    }
}
