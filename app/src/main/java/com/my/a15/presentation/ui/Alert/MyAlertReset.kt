package com.my.a15.presentation.ui.Alert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.my.a15.R
import com.my.a15.domain.model.VariantGrid
import com.my.a15.presentation.GameViewModel

@Composable
fun MyAlertReset(
    modifier: Modifier = Modifier,
    isShowAlertReset: MutableState<Boolean>,
    gameViewModel: GameViewModel,
) {
    val onDismissRequest: () -> Unit = { isShowAlertReset.value = isShowAlertReset.value.not() }
    if (isShowAlertReset.value)
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AlertDialog(
                containerColor = MaterialTheme.colorScheme.background,
                title = { Text(text = stringResource(R.string.reset_game)) },
                text = {
                    val list = listOf(
                        VariantGrid.GRID_4X4,
                        VariantGrid.GRID_5X5,
                        VariantGrid.GRID_6X6
                    )
                    LazyRow(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        items(list) { item ->
                            OutlinedButton(
                                modifier = modifier.padding(8.dp).size(128.dp),
                                shape = MaterialTheme.shapes.small,
                                onClick = {
                                    onDismissRequest()
                                    gameViewModel.restartGame(variantGrid = item)
                                },
                                content = {
                                    Text(item.toString())
                                }
                            )
                        }
                    }
                },
                shape = MaterialTheme.shapes.small,
                dismissButton = {
                    OutlinedButton(
                        shape = MaterialTheme.shapes.small,
                        onClick = { onDismissRequest() },
                        content = { Text(stringResource(R.string.no)) },
                    )
                },
                onDismissRequest = { onDismissRequest() },
                confirmButton = {

                }
            )
        }
}

