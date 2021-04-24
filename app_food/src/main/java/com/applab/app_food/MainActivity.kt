package com.applab.app_food

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.title.*
import kotlinx.android.synthetic.main.title.view.*

class MainActivity : AppCompatActivity() {
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this

        service()
    }
    private fun service() {
        // 1. 擷取 資料夾下面的 foods.json
        var json = assets.open("foods.json").bufferedReader().use { it.readText() }
        Log.d("MainActivity", json)
        // 2. json 字串轉成 foods 陣列(List)
        var foods = Gson().fromJson(json, Array<Food>::class.java).toList()
        Log.d("MainActivity", foods.toString())
        // 3. 建立適配器 adapter 給 grid_view 使用
        val adapter = object : ArrayAdapter<Food>(
            context,
            R.layout.item,
            R.id.text_name,
            foods) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    // v 這裡指的就是 R.layout.item 所配置的物件
                    val v = super.getView(position, convertView, parent)
                    val food = getItem(position) // 得到food 物件資料
                    val textName = v.text_name
                    val textPrice = v.text_price
                    val imgFood = v.image_food
                    val imageSpicy = v.image_spicy
                    val imageNew = v.image_new

                    val imgFoodId = resources.getIdentifier(food?.idName, "drawable", packageName)
                    textName.text = food?.name
                    textPrice.text = food?.price.toString()
                    imgFood.setImageResource(imgFoodId)

                    if(food!!.spicy) {
                        imageSpicy.setImageResource(R.drawable.isspicy)
                    } else {
                        imageSpicy.setImageResource(android.R.color.transparent)
                    }

                    if (food!!.new) {
                        imageNew.setImageResource(R.drawable.isnew)
                    } else {
                        imageNew.setImageResource(android.R.color.transparent)
                    }

                    return v
                }
            }
        // 4. 配置 adapter 給 grid_view
        grid_view.adapter = adapter

        // grid_view onItemClick 監聽
        grid_view.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val food = parent?.getItemAtPosition(position) as Food
                val imageId = resources.getIdentifier(food.idName, "drawable", packageName)
                image_food.setImageResource(imageId)

                text_name.text = food.name
                text_price.text = food.price.toString()

                if (food.spicy) {
                    image_spicy.setImageResource(R.drawable.isspicy)
                } else {
                    image_spicy.setImageResource(android.R.color.transparent)
                }

                if (food.new) {
                    image_new.setImageResource(R.drawable.isnew)
                } else {
                    image_new.setImageResource(android.R.color.transparent)
                }

            }
    }


    // homework

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_food -> true
            R.id.action_drink -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}