package com.example.kei.keidictionary;

public class WordEntry {
    private String word;
    private String mean;
    private int dict_id;
    private long serial;

    public  WordEntry(String word,String mean,int dictId){
        this.word = word;
        this.mean = mean;
        this.dict_id = dictId;
    }

    public String getWord(){
        return this.word;
    }

    public String getMean(){
        return this.mean;
    }

    public int getDict_id() {
        return dict_id;
    }

    public long getSerial() {
        return serial;
    }
}
