package com.thenody.streamdl.presentation.features.grab.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thenody.streamdl.domain.model.VideoInfo
import com.thenody.streamdl.presentation.theme.AppTheme

@Composable
fun DownloadDialog(
    videoInfo: VideoInfo,
    onDismiss: () -> Unit,
    onDownload: (String) -> Unit
) {
    var selectedUrl by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Выберите качество",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Column {
                Text(
                    videoInfo.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(16.dp))

                videoInfo.streams.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = selectedUrl == option.url,
                            onClick = { selectedUrl = option.url },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = AppTheme.extraColors.youtube,
                                unselectedColor = MaterialTheme.colorScheme.outline
                            )
                        )
                        Text(
                            option.quality,
                            modifier = Modifier.padding(start = 8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { selectedUrl?.let { onDownload(it) } },
                enabled = selectedUrl != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.extraColors.youtube,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Скачать")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "Отмена",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    )
}
