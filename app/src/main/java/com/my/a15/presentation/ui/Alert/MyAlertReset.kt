package com.my.a15.presentation.ui.Alert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.my.a15.data.VariantGrid
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
                        VariantGrid.GRID_16,
                        VariantGrid.GRID_25,
                        VariantGrid.GRID_36
                    )
                    LazyRow(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        items(list) { item ->
                            OutlinedButton(
                                modifier = modifier.padding(8.dp),
                                shape = MaterialTheme.shapes.small,
                                onClick = {
                                    onDismissRequest()
                                    gameViewModel.restartGame(grid = item)
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