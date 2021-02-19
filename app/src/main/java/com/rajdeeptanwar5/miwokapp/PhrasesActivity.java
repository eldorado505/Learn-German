package com.rajdeeptanwar5.miwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);

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

        ArrayList<Word> Phrases=new ArrayList<>();

        Phrases.add(new Word("Where are you going?","Wohin gehst du?",R.raw.phrase_where_are_you_going));
        Phrases.add(new Word("What is your name?","Wie heißen Sie?",R.raw.phrase_what_is_your_name));
        Phrases.add(new Word("My name is...","Mein Name ist...",R.raw.phrase_my_name_is));
        Phrases.add(new Word("How are you feeling?","Wie fühlst du dich?",R.raw.phrase_how_are_you_feeling));
        Phrases.add(new Word("I’m feeling good.","Ich fühle mich gut.",R.raw.phrase_im_feeling_good));
        Phrases.add(new Word("Are you coming?","Kommst du?",R.raw.phrase_are_you_coming));
        Phrases.add(new Word("Yes, I’m coming.","Ja, ich komme.",R.raw.phrase_yes_im_coming));
        Phrases.add(new Word("I’m coming.","Ich komme.",R.raw.phrase_im_coming));
        Phrases.add(new Word("Let’s go.","Lass uns gehen.",R.raw.phrase_lets_go));
        Phrases.add(new Word("Come here.","Komm her.",R.raw.phrase_come_here));
//        for(int i=0;i<Phrases.size();i++) {
//            Log.v("PhrasesActivity",i+" index has "+Phrases.get(i).getMiwokWord()+" " + Phrases.get(i).getDefaultWord()+" "+Phrases.get(i).getmImageResourceId());
//        }
        WordAdapter itemsAdapter=new WordAdapter(this,Phrases,R.color.category_phrases);

        ListView listView=(ListView) findViewById(R.id.list_view);
        listView.setAdapter(itemsAdapter);

        MediaPlayer.OnCompletionListener mOnCompletionListener=new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word item=Phrases.get(position);
                releaseMediaPlayer();
                int audioFocusRequest=audioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(audioFocusRequest==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, item.getmAudioResourceId());
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