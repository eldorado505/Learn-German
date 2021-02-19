package com.rajdeeptanwar5.miwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        audioManager= (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mOnAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if(focusChange==AudioManager.AUDIOFOCUS_GAIN&&mediaPlayer!=null) {
                    mediaPlayer.start();
                }
                else if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK||focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT&&mediaPlayer!=null) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
                else {
                    releaseMediaPlayer();
                }
            }
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        ArrayList<Word> Colours=new ArrayList<>();

        Colours.add(new Word("Red","Rot",R.drawable.color_red,R.raw.color_red));
        Colours.add(new Word("Green","Grün",R.drawable.color_green,R.raw.color_green));
        Colours.add(new Word("Brown","Braun",R.drawable.color_brown,R.raw.color_brown));
        Colours.add(new Word("Gray","Grau",R.drawable.color_gray,R.raw.color_gray));
        Colours.add(new Word("Black","Schwarz",R.drawable.color_black,R.raw.color_black));
        Colours.add(new Word("White","Weiß",R.drawable.color_white,R.raw.color_white));
        Colours.add(new Word("Dusty Yellow","Staubgelb",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        Colours.add(new Word("Mustard Yellow","Senfgelb",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        WordAdapter itemsAdapter=new WordAdapter(this,Colours,R.color.purple_700);

        ListView listView=(ListView) findViewById(R.id.list_view);
        listView.setAdapter(itemsAdapter);

        MediaPlayer.OnCompletionListener onCompletionListener=new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                releaseMediaPlayer();
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word item=Colours.get(position);
                releaseMediaPlayer();
                int requestAudioFocus=audioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(requestAudioFocus==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, item.getmAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
    }
    void releaseMediaPlayer( ) {
        if(mediaPlayer!=null) mediaPlayer.release();
        audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}