package com.example.nome.ui.confirmDialog

import androidx.compose.runtime.State

interface IConfirmViewModel {
    val showConfirmDialog: State<Boolean>
    val isWaiting: State<Boolean>
    val waitingProgress: State<Float>

    fun showConfirmDelete(onConfirm: suspend () -> Unit)

    fun onConfirmDelete()

    fun dismissDialog()
}