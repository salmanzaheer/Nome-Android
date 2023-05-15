package com.example.nome.ui.dialogue

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface


class OnlinePresetDialog(private val context: Context) {

    fun showConfirmationDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("You are leaving the app going to YouTube. Are you sure?")
        builder.setPositiveButton("Sure", DialogInterface.OnClickListener { dialog, which ->

        })
        builder.setNegativeButton("Bring me back", DialogInterface.OnClickListener { dialog, which ->
        })
        builder.show()
    }
}
