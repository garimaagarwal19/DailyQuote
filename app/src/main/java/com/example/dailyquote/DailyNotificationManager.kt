package com.example.dailyquote

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.dailyquote.data.storage.QuoteEntity
import io.reactivex.schedulers.Schedulers

/**
 * Created by Garima Chamaria on 26 October,2020
 */

class DailyNotificationManager(private val appContext: Context) {

    private val notificationId = 1111
    private val channelId = "1212"
    private val channelName = "General"
    private val mNotificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val mRepository = MainRepository()

    @SuppressLint("CheckResult")
    fun scheduleNotification() {
        mRepository.getDailyQuoteSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe( {
                createAndSendNotification(it)
            }, {
                it.printStackTrace()
            })

    }

    private fun createAndSendNotification(entity: QuoteEntity) {
        val builder = getNotificationBuilder().setAutoCancel(false)
            .setSmallIcon(R.drawable.ic_notifications_bell)
            .setContentTitle("Today's Quote")
            .setContentText(entity.quote)
            .setStyle(NotificationCompat.BigTextStyle().bigText(entity.quote))

        mNotificationManager.notify(notificationId, builder.build())
    }

    private fun getNotificationBuilder() : NotificationCompat.Builder {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return NotificationCompat.Builder(appContext)
        }
        var channel = mNotificationManager.getNotificationChannel(channelId)
        if(channel == null) {
            channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            mNotificationManager.createNotificationChannel(channel)
        }
        return NotificationCompat.Builder(appContext, channel.id)
    }
}