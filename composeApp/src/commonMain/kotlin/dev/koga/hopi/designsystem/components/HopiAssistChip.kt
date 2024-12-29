package dev.koga.hopi.designsystem.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HopiAssistChip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    leadingIcon:  @Composable() (() -> Unit)? = null,
    trailingIcon:  @Composable() (() -> Unit)? = null
) {
    AssistChip(
        modifier = modifier,
        label = {
            Text(text = label)
        },
        border = AssistChipDefaults.assistChipBorder(
            enabled = true,
            borderColor = MaterialTheme.colorScheme.surface,
        ),
        colors = AssistChipDefaults.assistChipColors(
            labelColor = MaterialTheme.colorScheme.onBackground,
            leadingIconContentColor = MaterialTheme.colorScheme.onBackground
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        onClick = onClick
    )
}