import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import di.BOTTOM_NAVIGATION_ITEMS
import di.FEATURE_API_MODULES
import di.ScreenRoutesProvider
import navigation.BottomNavigationItem
import navigation.SpeakEasBottomNavigation
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.speak.easy.core.FeatureApi
import org.speak.easy.ui.core.SpeakEasyTopBar
import org.speak.easy.ui.core.theme.SpeakEasyTheme
import speakeasy.ui_core.generated.resources.Res
import speakeasy.ui_core.generated.resources.history
import speakeasy.ui_core.generated.resources.main
import speakeasy.ui_core.generated.resources.settings
import speakeasy.ui_core.generated.resources.speak_easy
import speakeasy.ui_core.generated.resources.translator

@Composable
@Preview
fun App(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) = SpeakEasyTheme {
    KoinContext {
        val featureSet: List<FeatureApi> = koinInject(FEATURE_API_MODULES)
        val bottomNavigationItemsList: List<BottomNavigationItem> = koinInject(BOTTOM_NAVIGATION_ITEMS)

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute by remember(navBackStackEntry) {
            derivedStateOf { navBackStackEntry?.destination?.route }
        }

        val topBarTitle by remember(navBackStackEntry) {
            derivedStateOf { navBackStackEntry?.topBarTitle() }
        }

        Scaffold(
            topBar = {
                if (topBarTitle != null) {
                    SpeakEasyTopBar(
                        title = stringResource(topBarTitle!!),
                        contentAlignment = Alignment.Center
                    )
                }
            },
            bottomBar = {
                SpeakEasBottomNavigation(
                    items = bottomNavigationItemsList,
                    currentRoute = currentRoute,
                    onItemClick = { currentNavigationItem ->
                        navController.navigate(currentNavigationItem.route) {
                            navController.graph.startDestinationRoute?.let { startDestinationRoute ->
                                popUpTo(startDestinationRoute) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        ) { paddings ->
            NavHost(
                modifier = modifier
                    .padding(paddings)
                    .fillMaxSize(),
                navController = navController,
                startDestination = ScreenRoutesProvider.getTranslatorRoute(),
            ) {
                featureSet.forEach { feature ->
                    feature.registerGraph(
                        navController = navController,
                        navGraphBuilder = this
                    )
                }
            }
        }
    }
}

private fun NavBackStackEntry?.topBarTitle(): StringResource? {
    return when (this?.destination?.route) {
        ScreenRoutesProvider.getTranslatorRoute() -> Res.string.translator
        ScreenRoutesProvider.getHistoryRoute() -> Res.string.history
        ScreenRoutesProvider.getSettingsRoute() -> Res.string.settings
        else -> null
    }
}