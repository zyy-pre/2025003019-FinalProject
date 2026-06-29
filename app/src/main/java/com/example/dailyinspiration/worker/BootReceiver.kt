package com.example.dailyinspiration.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.dailyinspiration.datastore.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == Intent.ACTION_MY_PACKAGE_REPLACED
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val prefs = UserPreferencesRepository(context)
                    val enabled = prefs.isDailyNotificationEnabled.first()
                    if (enabled) {
                        scheduleDailyNotification(context, true)
                    }
                } catch (_: Exception) {
                }
            }
        }
    }
}
