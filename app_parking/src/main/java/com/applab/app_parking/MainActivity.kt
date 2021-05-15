package com.applab.app_parking

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var json = assets.open("parking.json").bufferedReader().use { it.readText() }
        val jelement: JsonElement = JsonParser.parseString(json)
        var result = jelement.asJsonObject.getAsJsonObject("result")
        val records = result.getAsJsonArray("records")

        val listType: Type = object : TypeToken<List<Map<String?, Object?>?>?>() {}.type
        val list: List<Map<String, Object>> = Gson().fromJson(records.toString(), listType)
        Log.d("MainActivity", list.toString())

        setTitle(title.toString() + "( " + list.size + " )")
        recycle_view.adapter = SalesAdapter(list)
        recycle_view.layoutManager = LinearLayoutManager(this)
        // recycler 優化 (固定 item 尺寸)
        recycle_view.setHasFixedSize(true)
    }
}