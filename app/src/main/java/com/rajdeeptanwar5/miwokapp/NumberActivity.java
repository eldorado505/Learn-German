package com.rajdeeptanwar5.miwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NumberActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
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
        setContentView(R.layout.activity_number);

        ArrayList<Word> words=new ArrayList<>();

        words.add(new Word("One","Einer",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("Two","Zwie",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("Three","Drie",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("Four","Vier",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("Five","Fünf",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("Six","Sechs",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("Seven","Sieben",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("Eight","Acht",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("Nine","Neun",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("Ten","Zehn",R.drawable.number_ten,R.raw.number_ten));

        WordAdapter itemsAdapter = new WordAdapter(this, words,R.color.category_numbers);

        MediaPlayer.OnCompletionListener mOnCompletionListener=new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                releaseMediaPlayer();
            }
        };


        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(itemsAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word item=words.get(position);
                releaseMediaPlayer();
                int requestAudioFocus=audioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(requestAudioFocus==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(NumberActivity.this, item.getmAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mOnCompletionListener);
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