package com.example.words;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.zip.Inflater;

public class showWord_land extends AppCompatActivity {

    Word[] words;
    ListView myListView;
    Array list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_land);
        //初始化设置三个单词
        list = new Array(1);
        Word apple = new Word();
        apple.setWord("apple");
        apple.setMeaning("苹果");
        apple.setExample("This is an apple!");
        Word banana = new Word();
        banana.setWord("banana");
        banana.setMeaning("香蕉");
        banana.setExample("This is a banana!");
//        Word cat = new Word();
//        apple.setWord("cat");
//        apple.setExample("There is a cat!");

        list.add(0, apple);
        list.add(1, banana);
        words = new Word[list.getSize()];
        words = list.getData();
        for (int i = 0; i < words.length; i++) {
            Log.d("output:", words[i].getWord());
        }

        ClickList_land(words);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Intent intent = new Intent(showWord_land.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void ClickList_land(final Word[] data) {
        //data：目前列表的单词表（显示）
        int length = 0;

        for(int i = 0; i < data.length; i++){
            if(data[i] == null)
                break;
            length++;

        }
        final String[] wordShow = new String[length];
        for(int i = 0; i < wordShow.length; i++){
            wordShow[i] = data[i].getWord();
            Log.d("wordShow:", wordShow[i]);
        }
        adapter = new ArrayAdapter<String>(showWord_land.this, android.R.layout.simple_expandable_list_item_1, wordShow);
        myListView = (ListView)findViewById(R.id.wordList);
        //Log.d("wordList:", adapter.getItem(0));
        myListView.setAdapter(adapter);
        //Log.d("", "成功！！！！！！！！！！！！！！！！！！！！");

        //点击查看&修改
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final TextView showWord = (TextView) findViewById(R.id.wordShow_land);
                final TextView showMeaning = (TextView) findViewById(R.id.meaningShow_land);
                final TextView showExample = (TextView) findViewById(R.id.exampleShow_land);
                showWord.setText(words[position].getWord());
                showMeaning.setText(words[position].getMeaning());
                showExample.setText(words[position].getExample());


            }
        });

        //长按删除
        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder delete_builder = new AlertDialog.Builder(showWord_land.this);
                final LayoutInflater inflater2 = getLayoutInflater();
                final View delete_view = inflater2.inflate(R.layout.delete_word, null);
                delete_builder.setView(delete_view).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                        words = list.getData();
                        final String[] wordShow = new String[words.length];
                        for(int i = 0; i < words.length; i++){
                            if(words[i] == null)
                                break;
                            wordShow[i] = words[i].getWord();
                        }
                        for(int i = 0; i < wordShow.length; i++){
                            if(wordShow[i] == null)
                                break;
                            Log.d("!wordShow:", wordShow[i]);
                        }
                        ClickList_land(words);

                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                delete_builder.show();
                return true;
            }
        });
    }
}