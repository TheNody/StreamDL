package com.thenody.streamdl.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thenody.streamdl.R
import com.thenody.streamdl.presentation.theme.AppTheme

data class NavItem(val id: Int)

private val items = listOf(
    NavItem(R.drawable.ic_home),
    NavItem(R.drawable.ic_search),
    NavItem(R.drawable.ic_heart),
    NavItem(R.drawable.ic_bell),
    NavItem(R.drawable.ic_settings),
)

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp),
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f),
        tonalElevation = 4.dp
    ) {
        items.forEachIndexed { index, item ->
            val selected = index == selectedIndex
            NavigationBarItem(
                selected = selected,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.id),
                        contentDescription = null
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppTheme.extraColors.youtube,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomBarPreview() {
    Surface(color = MaterialTheme.colorScheme.background) {
        BottomBar(
            selectedIndex = 0,
            onItemSelected = {}
        )
    }
}
