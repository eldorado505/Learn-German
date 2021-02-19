package com.rajdeeptanwar5.miwokapp;

import androidx.annotation.NonNull;

public class Word {
    private String mMiwokWord,mDefaultWord;
    private int mImageResourceId,mAudioResourceId;
    public String getMiwokWord() {
        return mMiwokWord;
    }
    public String getDefaultWord() {
        return mDefaultWord;
    }
    public int getmImageResourceId() { return mImageResourceId; }

    @NonNull
    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultWord + '\'' +
                ", mMiwokTranslation='" + mMiwokWord + '\'' +
                ", mAudioResourceId=" + mAudioResourceId +
                ", mImageResourceId=" + mImageResourceId +
                '}';
    }

    public int getmAudioResourceId() {
        return mAudioResourceId;
    }
    public Word(String defaultWord,String miwokWord,int ImageResourceId,int AudioResourceId) {
        mAudioResourceId=AudioResourceId;
        mDefaultWord=defaultWord;
        mImageResourceId=ImageResourceId;
        mMiwokWord=miwokWord;
    }
    public Word(String defaultWord,String miwokWord,int AudioResourceId) {
        mAudioResourceId=AudioResourceId;
        mDefaultWord=defaultWord;
        mMiwokWord=miwokWord;
    }
}
