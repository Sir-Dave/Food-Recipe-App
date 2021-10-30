package com.sirdave.foodrecipeapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DefaultSnackBar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
){
    SnackbarHost(hostState = snackbarHostState,
        snackbar = { snackbarData ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    snackbarData.actionLabel?.let { actionLabel ->
                        TextButton(
                            onClick = {onDismiss()}
                        ) {
                            Text(text = actionLabel,
                                style = MaterialTheme.typography.body2,
                                color = Color.White)
                        }
                    }
                }
            ){
                Text(
                    text = snackbarData.message,
                    style = MaterialTheme.typography.body2,
                    color = Color.White)
            }
        },
        modifier = modifier
    )
}