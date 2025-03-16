package com.mun.manualnotifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.mun.manualnotifications.MainActivity.Companion.notificationManager

class ReplyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            val replyText = remoteInput.getCharSequence("key_text_reply").toString()

            Toast.makeText(context, "You replied : $replyText", Toast.LENGTH_SHORT).show()

            // Create a new notification showing the reply
            /*val repliedNotification = NotificationCompat.Builder(context, "messaging_channel_id")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("You replied: $replyText")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(104, repliedNotification)*/
        }else{
            Toast.makeText(context, "Marked as read", Toast.LENGTH_SHORT).show()
        }
    }
}