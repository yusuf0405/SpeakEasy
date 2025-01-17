package org.speak.easy.ui.components.components
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.speak.easy.core.designsystem.SpeakEasyTheme
import org.speak.easy.core.designsystem.dimens.MediumElevation
import org.speak.easy.core.navigation.BottomNavigationItem

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
        items.forEachIndexed { _: Int, navigationItem ->
            NavigationBarItem(
                selected = currentRoute == navigationItem.route || navigationItem.subRouts.contains(currentRoute),
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