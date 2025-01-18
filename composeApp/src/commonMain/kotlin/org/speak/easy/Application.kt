package org.speak.easy

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.speak.easy.core.FeatureApi
import org.speak.easy.core.designsystem.SpeakEasyTheme
import org.speak.easy.core.designsystem.ThemeType
import org.speak.easy.core.navigation.BottomNavigationItem
import org.speak.easy.core.navigation.Destination
import org.speak.easy.core.navigation.NavigationAction
import org.speak.easy.core.navigation.Navigator
import org.speak.easy.core.navigation.di.BOTTOM_NAVIGATION_ITEMS
import org.speak.easy.core.ui.ObserveAsEvents
import org.speak.easy.core.ui.SpeakEasyTopBar
import org.speak.easy.di.FEATURE_API_MODULES
import org.speak.easy.ui.components.components.SpeakEasBottomNavigation
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.about_app
import speakeasy.core.ui.generated.resources.arrow_right
import speakeasy.core.ui.generated.resources.change_theme
import speakeasy.core.ui.generated.resources.history
import speakeasy.core.ui.generated.resources.settings
import speakeasy.core.ui.generated.resources.translator
import org.speak.easy.domain.models.ThemeType as Theme

typealias CurrentRoute = String

@Composable
fun Application(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val viewModel = koinInject<ApplicationViewModel>()
    val theme by viewModel.themeState.collectAsStateWithLifecycle()
    SpeakEasyTheme(
        theme = when (theme) {
            Theme.DARK -> ThemeType.DARK
            Theme.LIGHT -> ThemeType.LIGHT
            Theme.SYSTEM -> ThemeType.SYSTEM
        }
    ) {
        KoinContext {
            val featureSet: List<FeatureApi> = koinInject(FEATURE_API_MODULES)
            val bottomNavigationItemsList: List<BottomNavigationItem> =
                koinInject(BOTTOM_NAVIGATION_ITEMS)
            val navigator = koinInject<Navigator>()

            val currentRoute = navController.currentRoute()
            val topBarTitle = navController.topBarTitle()

            AppScaffold(
                topBarTitle = topBarTitle,
                bottomNavigationItemsList = bottomNavigationItemsList,
                currentRoute = currentRoute,
                navController = navController,
                onBottomNavItemClick = { route ->
                    navController.navigateSafely(route.route)
                }
            ) { paddings ->
                ObserveNavigationActions(navigator, navController)
                AppNavHost(
                    modifier = modifier.padding(paddings).fillMaxSize(),
                    navController = navController,
                    startDestination = navigator.startDestination.route,
                    featureSet = featureSet
                )
            }
        }
    }
}

@Composable
private fun AppScaffold(
    topBarTitle: StringResource?,
    bottomNavigationItemsList: List<BottomNavigationItem>,
    currentRoute: CurrentRoute?,
    navController: NavHostController,
    onBottomNavItemClick: (BottomNavigationItem) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            if (topBarTitle != null) {
                SpeakEasyTopBar(
                    title = stringResource(topBarTitle),
                    contentAlignment = Alignment.Center,
                    startIcon = currentRoute?.getStartIcon(),
                    onStartClick = navController::navigateUp
                )
            }
        },
        bottomBar = {
            SpeakEasBottomNavigation(
                items = bottomNavigationItemsList,
                currentRoute = currentRoute,
                onItemClick = onBottomNavItemClick
            )
        },
        content = content
    )
}

@Composable
private fun ObserveNavigationActions(
    navigator: Navigator,
    navController: NavHostController
) {
    ObserveAsEvents(navigator.navigationActions) { action ->
        when (action) {
            is NavigationAction.Navigate -> {
                navController.navigate(action.destination.route) {
                    action.navOptions(this)
                }
            }

            NavigationAction.NavigateUp -> navController.navigateUp()
        }
    }
}

@Composable
private fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String,
    featureSet: List<FeatureApi>
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        featureSet.forEach { feature ->
            feature.registerGraph(
                navController = navController,
                navGraphBuilder = this
            )
        }
    }
}

@Composable
private fun NavHostController.currentRoute(): CurrentRoute? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return remember(navBackStackEntry) { navBackStackEntry?.destination?.route }
}

@Composable
private fun CurrentRoute.getStartIcon(): Painter? {
    return when (this) {
        Destination.ChangeThemeScreen.route -> painterResource(Res.drawable.arrow_right)
        Destination.AboutAppScreen.route -> painterResource(Res.drawable.arrow_right)
        else -> null
    }
}

@Composable
private fun NavHostController.topBarTitle(): StringResource? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return remember(navBackStackEntry) {
        when (navBackStackEntry?.destination?.route) {
            Destination.TranslatorGraph.route -> Res.string.translator
            Destination.HistoryGraph.route -> Res.string.history
            Destination.SettingsScreen.route -> Res.string.settings
            Destination.ChangeThemeScreen.route -> Res.string.change_theme
            Destination.AboutAppScreen.route -> Res.string.about_app
            else -> null
        }
    }
}

private fun NavController.navigateSafely(route: String) {
    navigate(route) {
        graph.startDestinationRoute?.let { startDestinationRoute ->
            popUpTo(startDestinationRoute) { saveState = true }
        }
        launchSingleTop = true
        restoreState = true
    }
}