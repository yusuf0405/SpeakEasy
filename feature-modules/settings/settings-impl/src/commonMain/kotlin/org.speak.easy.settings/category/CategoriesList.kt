package org.speak.easy.settings.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import org.speak.easy.core.designsystem.SpeakEasyTheme

@Composable
internal fun CategoriesList(
    categories: List<Category>,
    onClick: (CategoryType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = SpeakEasyTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = SpeakEasyTheme.colors.backgroundModal
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = SpeakEasyTheme.dimens.dp8
        ),
        modifier = modifier
            .padding(SpeakEasyTheme.dimens.dp16)
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier.padding(SpeakEasyTheme.dimens.dp16)
        ) {
            categories.forEach { category ->
                key(category.type) {
                    CategoryItem(
                        category = category,
                        onClick = { onClick(category.type) }
                    )
                }
            }
        }
    }
}