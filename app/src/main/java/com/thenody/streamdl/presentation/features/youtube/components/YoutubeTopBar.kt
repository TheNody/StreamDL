package com.thenody.streamdl.presentation.features.youtube.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YoutubeTopBar(
    ui: YoutubeUiState,
    onBackToGrab: () -> Unit,
    onEnterSearch: () -> Unit,
    onExitSearch: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSubmitSearch: () -> Unit,
    onClear: () -> Unit,
    onExitResults: () -> Unit,
    onEditTitle: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    when {
        ui.isSearchActive -> {
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(Unit) { focusRequester.requestFocus() }

            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        onExitSearch()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Close")
                    }
                },
                title = {
                    TextField(
                        value = ui.searchQuery,
                        onValueChange = onQueryChange,
                        placeholder = { Text("Search YouTube") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                onSubmitSearch()
                                focusManager.clearFocus()
                            },
                            onDone = {
                                onSubmitSearch()
                                focusManager.clearFocus()
                            }
                        )
                    )
                },
                actions = {
                    IconButton(onClick = {
                        onClear()
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            )
        }

        ui.isSearchResults -> {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        onExitResults()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Text(
                        text = ui.lastSearchText,
                        modifier = Modifier.clickable {
                            onEditTitle()
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            onClear()
                            onEnterSearch()
                        }
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            )
        }

        else -> {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        onBackToGrab()
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                title = { Text(ui.selectedKiosk.title) },
                actions = {
                    IconButton(onClick = {
                        onEnterSearch()
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    }
}
