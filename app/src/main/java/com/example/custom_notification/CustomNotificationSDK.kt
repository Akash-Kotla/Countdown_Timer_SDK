package com.example.custom_notification


import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.CountDownTimer
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

//class CustomNotificationSDK(private val context: Context) {
//
//    private val notificationManager: NotificationManagerCompat =
//        NotificationManagerCompat.from(context)
//
//    // Define your notification channel ID here
//    private val CHANNEL_ID = "custom_notification_channel"
//    private val CHANNEL_NAME = "Custom Notification Channel"
//    private val CHANNEL_DESCRIPTION = "Channel for custom notifications"
//
//    init {
//        createNotificationChannel()
//    }
//
//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                CHANNEL_ID,
//                CHANNEL_NAME,
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            channel.description = CHANNEL_DESCRIPTION
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//
//
//    fun showNotification(title: String, content1: String, content2: String) {
//        val notificationLayout = RemoteViews(context.packageName, R.layout.custom_notification)
//        notificationLayout.setTextViewText(R.id.notificationTitle, title)
//        notificationLayout.setTextViewText(R.id.notificationContent1,content1)
//        notificationLayout.setTextViewText(R.id.notificationContent2, content2)
//        //notificationLayout.setTextViewText(R.id.countdownTimer, time)
//
//        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
//            .setSmallIcon(android.R.drawable.ic_dialog_info)
//            .setContentTitle(title)
//            .setContentText(content1)
//            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setCustomContentView(notificationLayout)
//            .setAutoCancel(true)
//            .build()
//
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.POST_NOTIFICATIONS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//
//            return
//        }
//        notificationManager.notify(NOTIFICATION_ID, notification)
//    }
//
//    companion object {
//        private const val NOTIFICATION_ID = 1
//    }
//}
class CustomNotificationSDK(private val context: Context) {

    private val notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    //  notification channel ID
    private val CHANNEL_ID = "custom_notification_channel"
    private val CHANNEL_NAME = "Custom Notification Channel"
    private val CHANNEL_DESCRIPTION = "Channel for custom notifications"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESCRIPTION
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotificationWithDynamicCountdown(
        title: String,
        durationMinutes: Int,
        content2: String
    ) {
        val notificationLayout = RemoteViews(context.packageName, R.layout.custom_notification)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCustomContentView(notificationLayout)
            .setAutoCancel(true)
            .build()

        val initialTimeInMillis = durationMinutes * 60 * 1000L
        updateNotificationContent(notificationLayout, initialTimeInMillis, content2)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        notificationManager.notify(NOTIFICATION_ID, notification)

        val timer = object : CountDownTimer(initialTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateNotificationContent(notificationLayout, millisUntilFinished, content2)
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return
                }
                notificationManager.notify(NOTIFICATION_ID, notification)
            }

            override fun onFinish() {
                updateNotificationContent(
                    notificationLayout,
                    0L,
                    "Time's up!"
                )
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                notificationManager.notify(NOTIFICATION_ID, notification)
            }
        }.start()
    }

    private fun updateNotificationContent(

        notificationLayout: RemoteViews,
        timeInMillis: Long,
        content2: String

    ) {
        val remainingMinutes = (timeInMillis / 60000).toInt()
        val minutes = remainingMinutes % 60
        val seconds = (timeInMillis / 1000 % 60).toInt()

        val formattedTime = String.format("%02d:%02d", minutes, seconds)
        val content1 = "$formattedTime Left, Order Now"


        notificationLayout.setTextViewText(R.id.notificationContent1, content1)
        notificationLayout.setTextViewText(R.id.notificationContent2, content2)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}










