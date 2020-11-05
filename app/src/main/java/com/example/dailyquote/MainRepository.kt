package com.example.dailyquote

import com.example.dailyquote.data.storage.AppDatabase
import com.example.dailyquote.data.storage.QuoteEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by Garima Chamaria on 15 October,2020
 */

class MainRepository {

    private val appDatabase = AppDatabase.getDatabase(MyApplication.getAppContext())

    fun insertQuoteObservable() : Completable {
        return Completable.fromAction {
            appDatabase.getQuoteDao().addQuotes(
                QuoteEntity()
                    .apply { quote = "Create a Positive Environment." },
                QuoteEntity()
                    .apply { quote = "Celebrate Your Small Wins." },
                QuoteEntity()
                    .apply { quote = "All our dreams can come true, if we have the courage to pursue them." },
                QuoteEntity()
                    .apply { quote = "The secret of getting ahead is getting started." },
                QuoteEntity()
                    .apply { quote = "It’s hard to beat a person who never gives up." }
            )
        }
    }

    fun getDailyQuoteSingle() : Single<QuoteEntity> {
        return Single.fromCallable {
            appDatabase.getQuoteDao().getQuote()
        }
    }
}