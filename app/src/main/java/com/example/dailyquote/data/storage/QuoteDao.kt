package com.example.dailyquote.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dailyquote.data.storage.QuoteEntity

/**
 * Created by Garima Chamaria on 03 October,2020
 */
@Dao
interface QuoteDao {
    @Insert
    fun addSingleQuote(quote: QuoteEntity)

    @Insert
    fun addQuotes(vararg quote: QuoteEntity)

    @Query("select * from quote_entity order by RANDOM() limit 1")
    fun getQuote() : QuoteEntity
}