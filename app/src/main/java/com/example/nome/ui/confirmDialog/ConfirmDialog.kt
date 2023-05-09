package com.example.nome.ui.confirmDialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.AndroidViewModel

@Composable
fun ConfirmDialog(
    title: String,
    text: String,
    confirmViewModel: IConfirmViewModel,
){
    val show by confirmViewModel.showConfirmDialog
    val waiting by confirmViewModel.isWaiting
    if (show){
        AlertDialog(
            onDismissRequest = { confirmViewModel.dismissDialog() },
            title = {
                if (waiting) {
                    LinearProgressIndicator(progress = confirmViewModel.waitingProgress.value)
                } else {
                    Text(text)
                }
            },
            confirmButton = {
                Button(
                    {confirmViewModel.onConfirmDelete()},
                    enabled = !confirmViewModel.isWaiting.value
                ) {
                    Text(text = "Delete")
                }
            }
        )
    }
}