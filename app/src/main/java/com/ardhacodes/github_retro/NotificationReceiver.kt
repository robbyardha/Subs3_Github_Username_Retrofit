package com.ardhacodes.github_retro

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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NotificationReceiver : BroadcastReceiver() {

    companion object{
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "Github Reminder"
        private const val NOTIFICATION_ID = 1
        private const val TIME_FORMAT = "HH:mm"
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_TYPE = "extra_type"
        private const val ID_REPEATING = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        sendNotification(context)
    }

    private fun sendNotification(context: Context) {
        var packageName = "com.ardhacodes.github_retro"
        var StringContexttext = "Waktunya untuk mencari Username Favorit anda di Github"
        val intent = context?.packageManager.getLaunchIntentForPackage(packageName)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(context.resources.getString((R.string.notification_name)))
            .setContentText(StringContexttext)
            .setAutoCancel(true)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)

        }
        val notification = builder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)

    }

    fun setMessageNotification(context: Context, type:String, time: String, message:String)
    {
        if (FormatDateInvalid(time, TIME_FORMAT)) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)


        val timeArr = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArr[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArr[1]))
        calendar.set(Calendar.SECOND, 0)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        var messageActive = "Notifikasi Alarm telah diaktifkan"
        Toast.makeText(context, messageActive, Toast.LENGTH_SHORT).show()

    }

    private fun FormatDateInvalid(time: String, timeFormat: String): Boolean
    {
        return try {
            val dateFormat = SimpleDateFormat(timeFormat, Locale.getDefault())
            dateFormat.isLenient = false
            dateFormat.parse(time)
            false
        }catch (e: ParseException){
            true
        }
    }

    fun cancelAlarm(context: Context)
    {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val reqCodes = ID_REPEATING
        val pendingIntent = PendingIntent.getBroadcast(context, reqCodes, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        var MessageContext = "Pengaturan Notifikasi Dibatalkan"
        Toast.makeText(context, MessageContext, Toast.LENGTH_SHORT).show()
    }
}