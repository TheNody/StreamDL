package com.thenody.streamdl.presentation.features.grab.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thenody.streamdl.R

@Composable
fun YoutubeCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(id = R.drawable.ic_youtube),
        contentDescription = "YouTube Logo",
        modifier = modifier
            .size(110.dp)
            .clickable { onClick() }
    )
}
