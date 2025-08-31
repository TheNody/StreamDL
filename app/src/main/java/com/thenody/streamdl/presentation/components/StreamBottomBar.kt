package com.thenody.streamdl.presentation.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thenody.streamdl.R

private val ActivePink = Color(0xFFFF6B6B)
private val InactiveGrey = Color(0xFF9EA7B2)

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
        modifier = modifier.fillMaxWidth(),
        containerColor = Color.White,
        tonalElevation = 0.dp
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
                    selectedIconColor = ActivePink,
                    unselectedIconColor = InactiveGrey,
                    indicatorColor = Color.Transparent,
                )
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun BottomBarPreview() {
    var selected by remember { mutableIntStateOf(0) }
    Surface(color = Color.White) {
        BottomBar(
            selectedIndex = selected,
            onItemSelected = { selected = it }
        )
    }
}