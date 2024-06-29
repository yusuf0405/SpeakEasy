import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import di.FEATURE_API_MODULES
import di.ScreenRoutesProvider
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.speak.easy.core.FeatureApi
import org.speak.easy.ui.core.theme.SpeakEasyTheme

@Composable
@Preview
fun App(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    KoinContext {
        val featureSet: List<FeatureApi> = koinInject(FEATURE_API_MODULES)
        SpeakEasyTheme {
            Scaffold {
                NavHost(
                    modifier = modifier.fillMaxSize(),
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
}