package com.my.a15.presentation.ui.Alert

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.my.a15.R
import kotlin.system.exitProcess

@Composable
fun MyAlertExit(
    modifier: Modifier = Modifier,
    isShowAlertExit: MutableState<Boolean>
) {
    val onDismissRequest: () -> Unit = { isShowAlertExit.value = isShowAlertExit.value.not() }

    if (isShowAlertExit.value)
        Box(
            modifier = modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AlertDialog(
                containerColor = MaterialTheme.colorScheme.background,
                title = {
                    Text(text = stringResource(R.string.reset_game))
                },
                shape = MaterialTheme.shapes.small,
                dismissButton = {
                    OutlinedButton(
                        shape = MaterialTheme.shapes.small,
                        onClick = {
                            onDismissRequest()
                        },
                        content = {
                            Text(stringResource(R.string.no))
                        },
                    )
                },
                onDismissRequest = {
                    onDismissRequest()
                },
                confirmButton = {
                    OutlinedButton(
                        shape = MaterialTheme.shapes.small,
                        onClick = {
                            exitProcess(0)
                        },
                        content = {
                            Text(stringResource(R.string.yes))
                        },
                    )

                }
            )
        }
}