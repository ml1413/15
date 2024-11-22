package com.my.a15.presentation.ui.Alert

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.my.a15.R

@Composable
fun MyAlertExit(
    modifier: Modifier = Modifier,
    isShowAlertExit: MutableState<Boolean>
) {
    val onDismissRequest: () -> Unit = { isShowAlertExit.value = isShowAlertExit.value.not() }
    val context = LocalContext.current
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
                    ElevatedButton(
                        shape = MaterialTheme.shapes.small,
                        onClick = {
                            onDismissRequest()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),

                        content = {
                            Text(
                                text = stringResource(R.string.no),
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.background
                            )
                        },
                    )
                },
                onDismissRequest = {
                    onDismissRequest()
                },
                confirmButton = {
                    ElevatedButton(
                        shape = MaterialTheme.shapes.small,
                        onClick = {
                            (context as Activity).finish()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
                        content = {
                            Text(
                                stringResource(R.string.yes), fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.background
                            )
                        },
                    )

                }
            )
        }
}