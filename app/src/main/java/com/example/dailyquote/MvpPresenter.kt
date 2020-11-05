package com.example.dailyquote

/**
 * Created by Garima Chamaria on 18 October,2020
 */

interface MvpPresenter {
    fun setView(view: MvpView)
    fun dropView()
    fun initialiseData()
}