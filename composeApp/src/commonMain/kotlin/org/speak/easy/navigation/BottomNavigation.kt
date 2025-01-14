package org.speak.easy.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.speak.easy.ui.core.theme.SpeakEasyTheme
import org.speak.easy.ui.core.theme.dimens.MediumElevation

@Composable
fun SpeakEasBottomNavigation(
    items: List<BottomNavigationItem>,
    currentRoute: String?,
    onItemClick: (BottomNavigationItem) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(MediumElevation),
        containerColor = SpeakEasyTheme.colors.backgroundModal,
        tonalElevation = MediumElevation
    ) {
        items.forEachIndexed { _, navigationItem ->
            NavigationBarItem(
                selected = currentRoute == navigationItem.route,
                onClick = { onItemClick(navigationItem) },
                icon = {
                    Icon(
                        painter = painterResource(navigationItem.icon),
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(navigationItem.title),
                        style = SpeakEasyTheme.typography.bodyMedium.medium,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SpeakEasyTheme.colors.iconsPrimary,
                    unselectedIconColor = SpeakEasyTheme.colors.iconsSecondary,
                    unselectedTextColor = SpeakEasyTheme.colors.iconsSecondary,
                    selectedTextColor = SpeakEasyTheme.colors.iconsPrimary,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}