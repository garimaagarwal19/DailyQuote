package com.example.dailyquote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity(), MvpView {

    private val mPresenter: MvpPresenter by lazy { MainPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter.setView(this)
        mPresenter.initialiseData()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMsg(t: Throwable) {
        Toast.makeText(this, "${t.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        mPresenter.dropView()
        super.onDestroy()
    }
}
