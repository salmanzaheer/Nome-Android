package com.example.nome.ui.dialogue

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

class OnlinePresetDialog(private val context: Context) {

    fun showConfirmationDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("You are leaving the app going to YouTube. Are you sure?")
        builder.setPositiveButton("Sure", DialogInterface.OnClickListener { dialog, which ->
            // User clicked Sure, start intent to open YouTube
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"))
            context.startActivity(intent)
        })
        builder.setNegativeButton("Bring me back", DialogInterface.OnClickListener { dialog, which ->
            // User clicked Bring me back, do nothing
        })
        builder.show()
    }
}
