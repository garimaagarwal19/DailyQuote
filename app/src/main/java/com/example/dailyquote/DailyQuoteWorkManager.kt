package com.example.dailyquote

import android.content.Context
import android.util.Log
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Garima Chamaria on 22 October,2020
 */

class DailyQuoteWorkManager(context: Context, params: WorkerParameters) : Worker(context, params) {
    private val mNotificationManager = DailyNotificationManager(MyApplication.getAppContext())

    companion object {
        private const val TAG = "TAG"

        fun scheduleWork(hour: String, min: String) {
            val requestTag = "${hour}_${min}_1"

            val scheduleCal = Calendar.getInstance()
            scheduleCal.set(Calendar.HOUR_OF_DAY, hour.toInt())
            scheduleCal.set(Calendar.MINUTE, min.toInt())
            var scheduleTimeMilliSec = scheduleCal.timeInMillis

            val currentCal = Calendar.getInstance()
            val currentTimeMilliSec = currentCal.timeInMillis

            if(scheduleTimeMilliSec < currentTimeMilliSec) {
                scheduleCal.add(Calendar.DAY_OF_MONTH, 1)
                scheduleTimeMilliSec = scheduleCal.timeInMillis
            }

            val offset = scheduleTimeMilliSec - currentTimeMilliSec

            val oneTimeWorkRequest = OneTimeWorkRequest.Builder(DailyQuoteWorkManager::class.java)
                .setInitialDelay(offset, TimeUnit.MILLISECONDS)
                .setInputData(Data.Builder().putString(TAG, requestTag).build())
                .addTag(requestTag)

            WorkManager.getInstance().enqueue(oneTimeWorkRequest.build())
        }

        private fun scheduleForNextDay(tag: String) {
            val arr = tag.split("_")
            val hour = arr[0].toInt()
            val min = arr[1].toInt()

            val scheduleCal = Calendar.getInstance()
            scheduleCal.add(Calendar.HOUR, hour)
            scheduleCal.add(Calendar.MINUTE, min)
            scheduleCal.add(Calendar.DAY_OF_MONTH, 1)

            val currentCal = Calendar.getInstance()
            val offset = scheduleCal.timeInMillis - currentCal.timeInMillis

            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<DailyQuoteWorkManager>()
                .setInitialDelay(offset, TimeUnit.MILLISECONDS)
                .setInputData(Data.Builder().putString(TAG, tag).build())
                .addTag(tag)

            WorkManager.getInstance().enqueue(oneTimeWorkRequest.build())
        }

    }

    override fun doWork(): Result {
        val tag = inputData.getString(TAG)
        tag?.let {
            mNotificationManager.scheduleNotification()
            scheduleForNextDay(it)
        }
        return Result.success()
    }
}