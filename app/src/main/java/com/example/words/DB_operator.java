package com.example.words;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DB_operator {

    private MyDatabaseHelper dbHelper;
    private String TAG = "MainActivity";

    public void insert(Word word){

        //创建SQLiteDatabase的对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //创建ContentValues对象，又来添加数据
        ContentValues values = new ContentValues();
        values.put("word",word.getWord());
        values.put("meaning", word.getMeaning());
        values.put("example", word.getExample());
        //插入数据（param1 :表名，param2：在未指定的数据的情况下赋值为空，params3：值）
        db.insert("words",null,values);

    }
    public void delete(String word){
        SQLiteDatabase dbDelete =dbHelper.getWritableDatabase();
        dbDelete.delete("words","word = ?",new String[] {word});


    }
    public void update(Word oldWord, Word newWord){
        SQLiteDatabase dbUpdate =dbHelper.getWritableDatabase();
        ContentValues values1 = new ContentValues();
        values1.put("meaning",newWord.getMeaning());
        dbUpdate.update("words",values1,"meaning = ?",new String[]{newWord.getMeaning()});
        values1.put("example",newWord.getExample());
        dbUpdate.update("words",values1,"example = ?",new String[]{newWord.getExample()});
    }
    public void query(String word){
        //创建SQLiteDatabase对象
        SQLiteDatabase db1 = dbHelper.getWritableDatabase();
        //使用游标，游历表中所有数据（params1：表名，params2：指定查询的列名，param3：指定where约束条件；params4：占位符具体值；下面的参数与分组，排序有关）
        Cursor cursor = db1.query("word",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                //取数据
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Log.e(TAG, "onClick: "+name);
            }while (cursor.moveToNext());
        }
        cursor.close();

    }
}
