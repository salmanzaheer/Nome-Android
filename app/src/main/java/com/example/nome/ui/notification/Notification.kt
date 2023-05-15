package com.example.nome.ui.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.nome.R

class Notification(private val context: Context) {

    private val CHANNEL_ID = "audio_channel"
    private var isPlaying = false

    init {
        // Create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Audio Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notification channel for audio"
                enableLights(true)
                lightColor = Color.GREEN
            }
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification() {
        isPlaying = !isPlaying
        val notificationMessage = if (isPlaying) "Nome app audio playing" else "audio stopped"
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(androidx.core.R.drawable.notification_action_background)
            .setContentTitle("Nome")
            .setContentText(notificationMessage)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }


}

