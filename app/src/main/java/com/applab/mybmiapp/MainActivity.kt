package com.applab.mybmiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var height: EditText // 晚一點設定 height
    lateinit var weight: EditText
    lateinit var result_view: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        height = findViewById(R.id.tf_height)
        weight = findViewById(R.id.tf_weight)
        result_view = findViewById/*<EditText>*/(R.id.result_View)
    }
    // 使用者按下 btn_calc 所要做的事
    fun calc(view: View) {
        val h = height.text.toString().toDouble()
        val w = weight.text.toString().toDouble()
        val bmi = w / Math.pow(h/100, 2.0)
        // 利用 Toast 顯示 bmi 的計算值
        Toast.makeText(this, "%.2f".format(bmi), Toast.LENGTH_SHORT).show()
        result_view.text = "%.2f".format(bmi)
    }
}