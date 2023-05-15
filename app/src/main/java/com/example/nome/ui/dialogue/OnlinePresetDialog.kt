package com.example.nome.ui.dialogue

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri


class OnlinePresetDialog(private val context: Context) {

    fun showConfirmationDialog(url: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("You are leaving the app going to YouTube. Are you sure?")
        builder.setPositiveButton("Go to YouTube", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        })
        builder.setNegativeButton("Stay here", DialogInterface.OnClickListener { dialog, which ->
        })
        builder.show()
    }
}
