package com.example.dailyquote.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Garima Chamaria on 26 September,2020
 */

@Database(entities = [QuoteEntity::class],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var mInstance: AppDatabase? = null
        private const val DB_NAME = "my_database"

        fun getDatabase(context: Context) : AppDatabase {
            if(mInstance == null) {
                mInstance = Room.databaseBuilder(context, AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return mInstance!!
        }
    }

    abstract fun getQuoteDao() : QuoteDao
}