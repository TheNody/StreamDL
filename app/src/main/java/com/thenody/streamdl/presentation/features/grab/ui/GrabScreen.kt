package com.thenody.streamdl.presentation.features.grab.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.thenody.streamdl.core.navigation.AppScaffold
import com.thenody.streamdl.core.navigation.Screen
import com.thenody.streamdl.presentation.features.grab.ui.components.DownloadDialog
import com.thenody.streamdl.presentation.features.grab.ui.components.YoutubeCard
import com.thenody.streamdl.presentation.features.grab.vm.GrabViewModel
import com.thenody.streamdl.presentation.theme.AppTheme

@Composable
fun GrabScreen(
    navController: NavHostController,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    viewModel: GrabViewModel = hiltViewModel()
) {
    val videoInfo by viewModel.videoInfo.collectAsState()

    var url by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val isUrlValid = remember(url) { url.startsWith("http") || url.isBlank() }

    AppScaffold(
        selectedIndex = selectedIndex,
        onTabSelected = onTabSelected
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                YoutubeCard(
                    onClick = { navController.navigate(Screen.YoutubeClient.route) }
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("YouTube ссылка") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = !isUrlValid && url.isNotBlank(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Search
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        errorBorderColor = MaterialTheme.colorScheme.error,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )

                if (!isUrlValid && url.isNotBlank()) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Некорректная ссылка",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        viewModel.fetchVideoInfo(url)
                        showDialog = true
                    },
                    enabled = url.isNotBlank() && isUrlValid,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.extraColors.youtube,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Найти видео")
                }
            }

            if (showDialog && videoInfo != null) {
                DownloadDialog(
                    videoInfo = videoInfo!!,
                    onDismiss = { showDialog = false },
                    onDownload = { streamUrl ->
                        viewModel.startDownload(videoInfo!!.title, streamUrl)
                        showDialog = false
                    }
                )
            }
        }
    }
}