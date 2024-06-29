package org.speak.easy.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.speak.easy.history.components.HistoryItem
import org.speak.easy.ui.core.components.SearchTextField
import org.speak.easy.ui.core.theme.SpeakEasyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HistoryScreen(
    uiState: HistoryUiState,
    onAction: (HistoryScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(SpeakEasyTheme.colors.backgroundPrimary),
        contentPadding = PaddingValues(SpeakEasyTheme.dimens.dp8),
        verticalArrangement = Arrangement.spacedBy(SpeakEasyTheme.dimens.dp12)
    ) {
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SpeakEasyTheme.colors.backgroundPrimary)
                    .padding(top = SpeakEasyTheme.dimens.dp8)
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = SpeakEasyTheme.dimens.dp4
                    ),
                    shape = RoundedCornerShape(SpeakEasyTheme.dimens.dp12)
                ) {
                    SearchTextField(
                        value = uiState.searchQuery,
                        onValueChange = { onAction(HistoryScreenAction.OnSearch(it)) },
                    )
                }
            }
        }
        items(
            items = uiState.histories,
            key = { it.id }
        ) { item ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { swipeState ->
                    when (swipeState) {
                        SwipeToDismissBoxValue.EndToStart -> {
                            onAction(HistoryScreenAction.OnDeleteIcon(item))
                        }

                        else -> return@rememberSwipeToDismissBoxState false
                    }
                    return@rememberSwipeToDismissBoxState true
                },
                positionalThreshold = { it * .25f }
            )

            HistoryItem(
                modifier = Modifier.animateItemPlacement(),
                item = item,
                onMoreClick = { onAction(HistoryScreenAction.OnMoreIconClick(item)) },
                dismissState = dismissState
            )
        }
    }
}