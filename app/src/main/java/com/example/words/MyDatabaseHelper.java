package com.example.words;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    //创建stu表的语句
    public static final String CREATE_BOOK = "create table words(" +
            "id integer primary key autoincrement," +
            "word text," +
            "meaning text," +
            "example text)" ;

    private Context context;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public MyDatabaseHelper(@Nullable MainActivity context, int version) {
        super(context, "words.db", null, version);
    }

    @Override
    //参数的dbOperator是操作数据库的对象
    public void onCreate(SQLiteDatabase dbOperator) {
        //创建数据库
        dbOperator.execSQL(CREATE_BOOK);
        Toast.makeText(context,"Create successful", Toast.LENGTH_SHORT).show();
    }

    //当传入的版本号大于当前数据库的版本号时调用
    //用于更新数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("TAG", "onUpgrade: ");
    }
}
