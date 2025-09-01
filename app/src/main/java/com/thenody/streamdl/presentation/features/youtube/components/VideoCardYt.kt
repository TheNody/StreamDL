package com.thenody.streamdl.presentation.features.youtube.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.thenody.streamdl.domain.model.VideoListItem

@Composable
fun VideoCardYt(
    modifier: Modifier = Modifier,
    item: VideoListItem,
    onClick: () -> Unit,
    onChannelClick: ((String) -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clipToBounds()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.thumbnailUrl),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            val durationText = item.durationSeconds?.let { formatDuration(it) }
            if (!durationText.isNullOrEmpty()) {
                Surface(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(6.dp)
                ) {
                    Text(
                        text = durationText,
                        color = MaterialTheme.colorScheme.surface,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(enabled = !item.channelUrl.isNullOrBlank()) {
                        item.channelUrl?.let { onChannelClick?.invoke(it) }
                    }
            ) {
                ChannelAvatar(avatarUrl = item.channelAvatarUrl, fallback = item.channel)
            }

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clipToBounds()
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true
                )
                Spacer(Modifier.height(4.dp))

                val meta = buildList {
                    if (item.channel.isNotBlank()) add(item.channel)
                    item.viewCount?.let { add(prettyViews(it)) }
                    item.publishedText?.takeIf { it.isNotBlank() }?.let { add(it) }
                }.joinToString(" • ")

                if (meta.isNotEmpty()) {
                    Text(
                        text = meta,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun ChannelAvatar(avatarUrl: String?, fallback: String) {
    if (!avatarUrl.isNullOrBlank()) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = "Channel avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
    } else {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = fallback.firstOrNull()?.uppercase() ?: "•",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatDuration(seconds: Long): String {
    val h = seconds / 3600
    val m = (seconds % 3600) / 60
    val s = seconds % 60
    return if (h > 0) "%d:%02d:%02d".format(h, m, s) else "%d:%02d".format(m, s)
}

@SuppressLint("DefaultLocale")
private fun prettyViews(v: Long): String {
    return when {
        v >= 1_000_000_000 -> String.format("%.1f млрд просмотров", v / 1_000_000_000.0)
        v >= 1_000_000     -> String.format("%.1f млн просмотров", v / 1_000_000.0)
        v >= 1_000         -> String.format("%.1f тыс. просмотров", v / 1_000.0)
        else               -> "$v просмотров"
    }
}
