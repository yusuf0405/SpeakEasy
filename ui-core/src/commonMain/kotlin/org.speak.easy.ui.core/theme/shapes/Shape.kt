package org.speak.easy.ui.core.theme.shapes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import org.speak.easy.ui.core.theme.dimens.LargeSpacing
import org.speak.easy.ui.core.theme.dimens.MediumSpacing
import org.speak.easy.ui.core.theme.dimens.SmallSpacing

val Shapes = Shapes(
    small = RoundedCornerShape(SmallSpacing),
    medium = RoundedCornerShape(MediumSpacing),
    large = RoundedCornerShape(LargeSpacing)
)