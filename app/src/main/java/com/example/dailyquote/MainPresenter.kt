package com.example.dailyquote

import com.example.dailyquote.data.local.SharedPrefManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Garima Chamaria on 05 October,2020
 */

class MainPresenter : MvpPresenter{

    private val mainRepository = MainRepository()
    private var mView: MvpView? = null
    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun setView(view: MvpView) {
        mView = view
    }

    override fun initialiseData() {
        if(SharedPrefManager.isFirstLaunch) {
            insertNewQuotes()
        } else {
            mView?.showMessage("DB already exists")
        }
    }

    private fun insertNewQuotes() {
        val disposable = mainRepository.insertQuoteObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                SharedPrefManager.isFirstLaunch = false
                mView?.showMessage("DB Created Successfully")
                DailyQuoteWorkManager.scheduleWork("17","00")
            }, {
                it.printStackTrace()
                mView?.showErrorMsg(it)
            })
        mCompositeDisposable.add(disposable)
    }

    override fun dropView() {
        mView = null
        mCompositeDisposable.clear()
    }
}