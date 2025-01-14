package org.speak.easy.core.designsystem.shapes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import org.speak.easy.core.designsystem.dimens.MediumSpacing
import org.speak.easy.core.designsystem.dimens.LargeSpacing
import org.speak.easy.core.designsystem.dimens.SmallSpacing

val Shapes = Shapes(
    small = RoundedCornerShape(SmallSpacing),
    medium = RoundedCornerShape(MediumSpacing),
    large = RoundedCornerShape(LargeSpacing)
)