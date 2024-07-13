package org.speak.easy.ui.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.speak.easy.ui.core.theme.SpeakEasyTheme

@Composable
fun FlagItem(
    flag: DrawableResource,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(SpeakEasyTheme.dimens.dp24)
            .clip(CircleShape),
        painter = painterResource(flag),
        contentDescription = null
    )
}