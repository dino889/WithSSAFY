package com.ssafy.withssafy.src.network.service

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.src.login.SingInActivity
import com.ssafy.withssafy.src.main.MainActivity


private const val TAG = "FCMService_ws"
class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: $token")
        SingInActivity.uploadToken(token, ApplicationClass.sharedPreferencesUtil.getUser().id)
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        Log.d(TAG, "onMessageReceived: ${remoteMessage.from}, ${remoteMessage.notification} , ${remoteMessage.data}")

        if(remoteMessage.notification != null) {
            val messageTitle = remoteMessage.notification!!.title
            val messageContent = remoteMessage.notification!!.body
            val mainIntent = Intent(this, SingInActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val mainPendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, mainIntent, 0)

            val builder1 = NotificationCompat.Builder(this, SingInActivity.channel_id)
                .setSmallIcon(R.drawable.ic_app_icon)
                .setContentTitle(messageTitle)
                .setContentText(messageContent)
                .setStyle(NotificationCompat.BigTextStyle().bigText(messageContent))
                .setAutoCancel(true)
                .setColor(Color.argb(0,47, 61, 168))
               .setContentIntent(mainPendingIntent)

            NotificationManagerCompat.from(this).apply {
                notify(101, builder1.build())
            }

            val intent = Intent("com.ssafy.withssafy")
            sendBroadcast(intent)
        }


        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }
}