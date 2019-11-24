package com.example.words;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WordsProvider extends ContentProvider {

    private Context context;
    private MyDatabaseHelper myhelp=null;
    SQLiteDatabase Pbase =null;
    public static final String  AUTOHORITY = "com.example.vocabularybook";
    public static final int Words_Code =1;

    private static final UriMatcher mMacher;
    static {
        mMacher = new UriMatcher(UriMatcher.NO_MATCH);
        mMacher.addURI(AUTOHORITY,"words",Words_Code);
    }
    @Override
    public boolean onCreate() {
        context=getContext();
        myhelp=new MyDatabaseHelper(getContext());
        Pbase=myhelp.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String table ="words";

        return Pbase.query(table,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String table = "words";
        Pbase.insert(table, null, values);
        context.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
