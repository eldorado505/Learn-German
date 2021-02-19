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

public class FamilyActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_family);

        ArrayList<Word> FamilyMembers=new ArrayList<>();

        FamilyMembers.add(new Word("Father","Vater",R.drawable.family_father,R.raw.family_father));
        FamilyMembers.add(new Word("Mother", "Mutter",R.drawable.family_mother,R.raw.family_mother));
        FamilyMembers.add(new Word("Son","Sohn",R.drawable.family_son,R.raw.family_son));
        FamilyMembers.add(new Word("Daughter","Tochter",R.drawable.family_daughter,R.raw.family_daughter));
        FamilyMembers.add(new Word("Elder Brother","Älterer Bruder",R.drawable.family_older_brother,R.raw.family_older_brother));
        FamilyMembers.add(new Word("Younger Brother","Jüngerer Bruder",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        FamilyMembers.add(new Word("Elder Sister","Ältere Schwester",R.drawable.family_older_sister,R.raw.family_older_sister));
        FamilyMembers.add(new Word("Younger Sister","Jüngere Schwester",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        FamilyMembers.add(new Word("Grandmother","Oma",R.drawable.family_grandmother,R.raw.family_grandmother));
        FamilyMembers.add(new Word("Grandfather","Großvater",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter itemsAdapter=new WordAdapter(this,FamilyMembers,R.color.category_family);

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
                Word item=FamilyMembers.get(position);
              releaseMediaPlayer();
              int audioFocusRequest=audioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
              if(audioFocusRequest==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                  mediaPlayer = MediaPlayer.create(FamilyActivity.this, item.getmAudioResourceId());
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