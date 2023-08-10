package com.example.custom_notification

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity2 : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//
//        val customNotificationSDK = CustomNotificationSDK(this)
//
//        val title = "Brand Push Notification "
//        val content1 = "Left, Order Now "
//        val content2 = "Get 15% OFF before it expires"
// customNotificationSDK.showNotification(title, content1,content2)


        val customNotificationSDK = CustomNotificationSDK(this)

        val title = "Brand Push Notification"
        val durationMinutes = 30 // Set your duration in minutes
        val content2 = "Get 15% OFF before it expires"

        customNotificationSDK.showNotificationWithDynamicCountdown(title, durationMinutes, content2)

    }
}