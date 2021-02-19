package com.rajdeeptanwar5.miwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView Numbers=(TextView) findViewById(R.id.numbers);
        Numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent sendIntent=new Intent(v.getContext(),NumberActivity.class);
                startActivity(sendIntent);
            }
        });
        TextView Family=(TextView) findViewById(R.id.family);
        Family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent=new Intent(v.getContext(),FamilyActivity.class);
                startActivity(sendIntent);
            }
        });
        TextView Phrases=(TextView) findViewById(R.id.phrases);
        Phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent=new Intent(v.getContext(),PhrasesActivity.class);
                startActivity(sendIntent);
            }
        });
        TextView Colors=(TextView) findViewById(R.id.colors);
        Colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent=new Intent(v.getContext(),ColorsActivity.class);
                startActivity(sendIntent);
            }
        });

    }

    public void openNumbersList(View view) {
        Intent sendIntent=new Intent(this,NumberActivity.class);
        startActivity(sendIntent);
    }

    public void openFamilyList(View view) {
        Intent sendIntent=new Intent(this,FamilyActivity.class);
        startActivity(sendIntent);
    }

    public void openPhrasesList(View view) {
        Intent sendIntent=new Intent(this,PhrasesActivity.class);
        startActivity(sendIntent);
    }

    public void openColorsList(View view) {
        Intent sendIntent=new Intent(this,ColorsActivity.class);
        startActivity(sendIntent);
    }
}