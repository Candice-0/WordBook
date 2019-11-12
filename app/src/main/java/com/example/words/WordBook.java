package com.example.words;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class WordBook extends AppCompatActivity {

    ListView myListView;
    Word[] words;
    ArrayAdapter<String> adapter;
    Array list = new Array(1);
    int i = 0;
    String afterMeaning = "";
    String afterExample = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_book);

        //接收参数

        //Intent intent = getIntent();
        //String eml = intent.getStringExtra("usr");
        //String pwd = intent.getStringExtra("pwd");

        Bundle bundle = this.getIntent().getExtras();
        String word = bundle.getString("word");
        String meaning = bundle.getString("meaning");
        String example = bundle.getString("example");

        Log.d("getWord:", word);
        Log.d("getMeaning", meaning);
        Log.d("getExample", example);

        Word newWord = new Word();
        newWord.setWord(word);
        newWord.setMeaning(meaning);
        newWord.setExample(example);

        list.add(i, newWord);
        i++;
        words = list.getData();

        ClickList();


    }

    public void ClickList(){
        String[] wordShow = new String[list.getSize()];
        for(int i = 0; i < wordShow.length; i++){
            wordShow[i] = words[i].getWord();
        }

        adapter = new ArrayAdapter<String>(WordBook.this, android.R.layout.simple_expandable_list_item_1, wordShow);
        myListView = (ListView)findViewById(R.id.wordBook_list);
        //Log.d("wordList:", adapter.getItem(0));
        myListView.setAdapter(adapter);

        //点击查看&修改
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                showWord(position);

            }
        });

        //长按删除
        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder delete_builder = new AlertDialog.Builder(WordBook.this);
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
                        ClickList();



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

        //返回键返回
        Button back = (Button)findViewById(R.id.wordBook_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WordBook.this, MainActivity.class);
                startActivity(intent);

            }


        });

        //添加键添加
        Button add = (Button)findViewById(R.id.wordBook_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(WordBook.this);
                final LayoutInflater inflater = getLayoutInflater();
                final View v1 = inflater.inflate(R.layout.add_words, null);
                builder.setView(v1).setTitle("添加单词").setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText word = v1.findViewById(R.id.addWord);
                        EditText meaning = v1.findViewById(R.id.addMeaning);
                        EditText example = v1.findViewById(R.id.addExample);
                        String word_inList = word.getText().toString();
                        String meaning_inList = meaning.getText().toString();
                        String example_inList = example.getText().toString();
                        Word newWord = new Word();
                        if(word_inList != ""){
                            newWord.setWord(word_inList);
                            newWord.setMeaning(meaning_inList);
                            newWord.setExample(example_inList);
                        }

                        list.add(words.length, newWord);
                        words = list.getData();
                        Word[] update = new Word[words.length];
                        int length = 0;
                        for(int j = 0; j < update.length; j++){
                            if(words[j] != null){
                                length++;
                                update[j] = words[j];
                            }
                        }
                        words = new Word[length];
                        for(int j = 0; j < length; j++){
                            words[j] = update[j];
                        }
                        ClickList();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            }


        });
    }

    public void showWord(final int position){
        setContentView(R.layout.word_show);
        final TextView wordShow = (TextView) findViewById(R.id.wordShow);
        final TextView meaningShow = (TextView)findViewById(R.id.meaningShow);
        final TextView exampleShow = (TextView)findViewById(R.id.exampleShow);

        wordShow.setText(words[position].getWord());
        meaningShow.setText(words[position].getMeaning());
        exampleShow.setText(words[position].getExample());

        //点击弹出修改提示框modify_builder
        Button modify = (Button)findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder modify_builder = new AlertDialog.Builder(WordBook.this);
                final LayoutInflater inflater1 = getLayoutInflater();
                final View modify_view = inflater1.inflate(R.layout.edit_words, null);
                modify_builder.setView(modify_view).setTitle("修改单词").setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editMeaning = modify_view.findViewById(R.id.editMeaning);
                        EditText editExample = modify_view.findViewById(R.id.editExample);

                        String newMeaning = editMeaning.getText().toString();
                        String newExample = editExample.getText().toString();
                        Log.d("newMeaning:", newMeaning);
                        Log.d("newExample:", newExample);

                        if(newMeaning != "" && newExample != ""){
                            meaningShow.setText(newMeaning);
                            exampleShow.setText(newExample);
                            afterMeaning = newMeaning;
                            afterExample = newExample;
                        }



                    }

                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                modify_builder.show();

            }

        });

        Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("afterMeaning:", afterMeaning);
                Log.d("afterExample:", afterExample);

                if(afterMeaning == "" && afterExample == ""){
                    setContentView(R.layout.word_book);
                    ClickList();
                }else{
                    words[position].setMeaning(afterMeaning);
                    words[position].setExample(afterExample);
                    list.set(position, words[position]);
                    words = list.getData();
                    for(int i = 0; i < words.length; i++){
                        if(words[i] == null)
                            break;
                        Log.d("after!!!:", words[i].getWord());
                        Log.d("after!!!:", words[i].getMeaning());
                        Log.d("after!!!:", words[i].getExample());

                    }
                    setContentView(R.layout.word_book);
                    ClickList();

                }

            }


        });

    }
}
