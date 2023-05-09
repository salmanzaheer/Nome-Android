package com.example.nome.ui.confirmDialog

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConfirmViewModel : ViewModel(), IConfirmViewModel {
    private val _showConfirmDialog: MutableState<Boolean> = mutableStateOf(false)
    private var _onConfirmDeleteCallback: (suspend () -> Unit)? = null
    override val showConfirmDialog: State<Boolean> = _showConfirmDialog
    private var _isWaiting = mutableStateOf(false)
    private var _waitingProgress = mutableStateOf(0.0f)
    override val isWaiting: State<Boolean> = _isWaiting
    override val waitingProgress: State<Float> = _waitingProgress

    override fun showConfirmDelete(onConfirm: suspend () -> Unit) {
        _showConfirmDialog.value = true
        _onConfirmDeleteCallback = onConfirm
    }

    override fun onConfirmDelete() {
        if(_onConfirmDeleteCallback != null) {
            viewModelScope.launch {
                val progressTask = viewModelScope.async { incrementProgress() }
                _isWaiting.value = true
                _onConfirmDeleteCallback?.invoke()
                _isWaiting.value = false
                progressTask.cancel()
                dismissDialog()
            }
        }
    }

    private suspend fun incrementProgress() {
        try {
            while (true) {
                delay(500)
                _waitingProgress.value += (1.0f / 10.0f)
            }
        } catch (e: CancellationException) {
            _waitingProgress.value = 0.0f
        }
    }

    override fun dismissDialog() {
        _showConfirmDialog.value = false
    }
}