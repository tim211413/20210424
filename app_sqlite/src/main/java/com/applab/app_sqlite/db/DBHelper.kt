package com.applab.app_sqlite.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.applab.app_sqlite.models.Student
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

// 撰寫與資料庫建立, 升級, 銷毀與CRUD相關程序
class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        // SingleTon(companion object 相對於 Java 的 static inner class 靜態內部類別)
        val DATABASE_VERSION = 1 // 資料庫版本
        val DATABASE_NAME = "MyDB.db" //資料庫名稱

        // 建立資料表 Student 的SQL語句
        val SQL_CREATE_STUDENT = "" +
                "CREATE TABLE Student(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "score INTEGER," +
                "ct BIGINT)"
        // 刪除資料表 Student 的SQL語句
        val SQL_DELETE_STUDENT = "DROP TABLE IF EXISTS Student"
    }
    // 預設資料表的建立
    override fun onCreate(db: SQLiteDatabase?) {
        // db 指的是 MyDB.db 資料庫
        db?.execSQL(SQL_CREATE_STUDENT)
    }

    // 資料庫升級
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_STUDENT)
        onCreate(db)

    }

    // 新增資料
    fun createStudent(name: String, score: Int) {
        // 取得資料庫
        val db = writableDatabase
        // 準備要新增的紀錄
        val values = ContentValues()
        values.put("name", name)
        values.put("score", score)
        values.put("ct", Date().time)
        // 新增到資料庫
        // nullColumnHack 是指若欄位是空白要放的內容為何 ?
        // action 此次新增的id值
        val action = db.insert("Student", null, values)
        db.close()
        Log.d("DB", "createStudent: action=" + action)
    }

    // 修改資料
    fun updateStudent(student: Student) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("id", student.id)
        values.put("name", student.name)
        values.put("score", student.score)
        values.put("ct", student.ct)
        // Where 條件
        val selection = "id LIKE ?"
        val selectionArgs = arrayOf(student.id.toString())
        // action 是指異動筆數
        val action = db.update("Student", values, selection, selectionArgs)
        db.close()
        Log.d("DB", "updateStudent: action=" + action)
    }

    // 刪除資料
    fun deleteStudent(id: Int) {
        val db = writableDatabase
        val selection = "id LIKE ?"
        val selectionArgs = arrayOf(id.toString())
        val action = db.delete("Student", selection, selectionArgs)
        db.close()
        Log.d("DB", "deleteStudent: action=" + action)
    }

    // 查詢資料
    fun readAllStudent(): List<Student> {
        val students = ArrayList<Student>()
        val db = readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT id, name, score, ct FROM Student", null)
            if(cursor!!.moveToFirst()) { // 將查詢指標移動到第一筆
                while (cursor.isAfterLast == false) { // 當查詢指標不是最後一筆時
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val score = cursor.getInt(cursor.getColumnIndex("score"))
                    val ct = cursor.getLong(cursor.getColumnIndex("ct"))
                    val student = Student(
                        id, name, score, ct
                    )
                    students.add(student)
                    cursor.moveToNext()
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
        db.close()
        return students
    }
}
