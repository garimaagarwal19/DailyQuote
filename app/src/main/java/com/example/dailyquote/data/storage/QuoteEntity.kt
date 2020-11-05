package com.example.dailyquote.data.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Garima Chamaria on 26 September,2020
 */
@Entity(tableName = "Quote_entity")
class QuoteEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var quote: String = ""
}
