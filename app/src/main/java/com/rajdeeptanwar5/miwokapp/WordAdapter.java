package com.rajdeeptanwar5.miwokapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorId;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem=convertView;
        if(listViewItem==null){
            listViewItem= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Word currentWord=getItem(position);
        TextView miwokWord=(TextView) listViewItem.findViewById(R.id.miwok_word);
        TextView defaultWord=(TextView) listViewItem.findViewById(R.id.default_word);
        ImageView image= (ImageView) listViewItem.findViewById(R.id.image);

        miwokWord.setText(currentWord.getMiwokWord());
        LinearLayout textContainer=(LinearLayout) listViewItem.findViewById(R.id.text_container);
        int colorValue= ContextCompat.getColor(getContext(),mColorId);
        textContainer.setBackgroundColor(colorValue);
        defaultWord.setText(currentWord.getDefaultWord());
        if(currentWord.getmImageResourceId()!=0) {
            image.setImageResource(currentWord.getmImageResourceId());

        }
        else {
            image.setVisibility(View.GONE);
        }
        return listViewItem;
    }
    public WordAdapter(Context context,ArrayList<Word> Words, int colorId) {
        super(context,0,Words);
        mColorId=colorId;
    }
}
