package com.example.words;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class showWord extends AppCompatActivity {

    String afterMeaning = "";
    String afterExample = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.word_show);
        final TextView wordShow = (TextView) findViewById(R.id.wordShow);
        final TextView meaningShow = (TextView)findViewById(R.id.meaningShow);
        final TextView exampleShow = (TextView)findViewById(R.id.exampleShow);

        //接受传参
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        wordShow.setText(bundle.getString("word"));
        meaningShow.setText(bundle.getString("meaning"));
        exampleShow.setText(bundle.getString("example"));

        //点击弹出修改提示框modify_builder
        Button modify = (Button)findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder modify_builder = new AlertDialog.Builder(showWord.this);
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
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("afterMeaning", afterMeaning);
                bundle.putString("afterExample", afterExample);
                intent.putExtras(bundle);
                intent.setClass(showWord.this, MainActivity.class);
                startActivity(intent);
//                setResult(2, intent);//返回值调用函数，其中2为resultCode，返回值的标志
//                finish();//传值结束
            }
        });
    }
}
