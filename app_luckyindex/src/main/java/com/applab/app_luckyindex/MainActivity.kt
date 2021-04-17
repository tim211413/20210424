package com.applab.app_luckyindex

import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ComponentActivity() {
    lateinit var context: Context
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
    }

    fun OnClick(view: View) {
        Log.d(TAG, inputNumber.text.toString())
        val intent = Intent(context, ResultActivity::class.java)
        // 將 inputNumber 所輸入的資料傳送到下一個 Activity (也就是 ResultActivity)
        intent.putExtra("number", inputNumber.text.toString())
        // 轉跳 Activity
        openResultActivityCustom.launch(intent)
    }

    private val openResultActivityCustom =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 101) {
                AlertDialog.Builder(context)
                    .setTitle(R.string.app_result_title)
                    .setMessage(result.data?.getStringExtra("luckyIndex").toString())
                    .setPositiveButton("OK", null)
                    .show()
            }
        }


//    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
//        val luckyIndex = intent?.getStringExtra("luckyIndex").toString()
//        AlertDialog.Builder(this)
//            .setTitle(R.string.app_result_title)
//            .setMessage(luckyIndex)
//            .setPositiveButton(R.string.app_submit, null)
//            .show()
//    }


}