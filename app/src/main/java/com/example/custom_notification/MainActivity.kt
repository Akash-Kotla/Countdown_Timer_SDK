package com.example.custom_notification

import android.os.Bundle
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val customNotificationSDK = CustomNotificationSDK(this)
       // customNotificationSDK.showNotification("Title", "Content 1", "Content 2",)

    }

}