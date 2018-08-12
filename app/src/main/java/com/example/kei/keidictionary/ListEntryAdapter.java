package com.example.kei.keidictionary;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListEntryAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<WordEntry> wordList;

    public ListEntryAdapter(Context context) {
        this.context = context;
        this.layoutInflater =  (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setWordList(ArrayList<WordEntry> wList){
        this.wordList = wList;
    }

    @Override
    public int getCount() {
        return wordList.size();
    }

    @Override
    public Object getItem(int position) {
        return wordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return wordList.get(position).getSerial();
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.entry_item,parent,false);
        ((TextView)convertView.findViewById(R.id.word)).setText(android.text.Html.fromHtml(wordList.get(position).getWord(), Html.FROM_HTML_MODE_COMPACT));
        ((TextView)convertView.findViewById(R.id.mean)).setText(android.text.Html.fromHtml(wordList.get(position).getMean(), Html.FROM_HTML_MODE_COMPACT));
        return convertView;
    }

}
