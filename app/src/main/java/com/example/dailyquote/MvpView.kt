package com.example.dailyquote

/**
 * Created by Garima Chamaria on 15 October,2020
 */

interface MvpView {
    fun showMessage(msg: String)
    fun showErrorMsg(t: Throwable)
}