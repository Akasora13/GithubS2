package id.Farrel.githubs2.service.Reminder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import id.Farrel.githubs2.R
import id.Farrel.githubs2.ui.home.HomeActivity
import java.util.*

class ReminderReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_MESSAGE =
            "Check us! and If u want to rest, walk. Just dont stop making progress!"
    }


    override fun onReceive(context: Context, intent: Intent) {
        showNotification(context)
    }

    fun setAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(
            context, 100, intent, PendingIntent.FLAG_ONE_SHOT
        )

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(context, R.string.toast_setReminder, Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 100, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, R.string.toast_unsetReminder, Toast.LENGTH_SHORT).show()

    }

    private fun showNotification(context: Context) {
        val chId = "githubS2"
        val chName = "Daily Reminder"

        val intent = Intent(context, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context, 0,
            intent, PendingIntent.FLAG_ONE_SHOT
        )

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, chId)
            .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
            .setContentTitle("Daily Reminder")
            .setContentText(EXTRA_MESSAGE)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel(
                chId,
                chName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(chId)
            notificationManagerCompat.createNotificationChannel(ch)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(100, notification)
    }

}