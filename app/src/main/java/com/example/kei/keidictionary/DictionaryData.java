package com.example.kei.keidictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DictionaryData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dictionary";
    private static final String TABLE_NAME = "master_dict";
    private static final int DATABASE_Version = 1;
    private static final String UID="id";
    private static final String DICT_ID = "dict_id";
    private static final String WORD = "word";
    private static final String MEAN = "mean";

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME +
            "( "+UID+" INTEGER PRIMARY KEY AUTOINCREMENT," + DICT_ID + " INTEGER ," + WORD +" TEXT," + MEAN + " TEXT)";


    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;

    private Context context;

    public DictionaryData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.context=context;
        //Message.message(context,"Reading database . . . ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            db.execSQL(CREATE_TABLE);
            //Message.message(context,"TABLE CREATED");
        } catch (Exception e) {
            Message.message(context,""+e);
        }
        Message.message(context,"TABLE CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Message.message(context,"OnUpgrade");
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (Exception e) {
            Message.message(context,""+e);
        }
    }

    public void addNewWord(WordEntry entry) {
        Log.d("Kei",entry.getDict_id() + "-"+entry.getWord());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DICT_ID,entry.getDict_id());
        values.put(WORD,entry.getWord());
        values.put(MEAN,entry.getMean());
        db.insert(TABLE_NAME,null, values);
        Log.d("Kei","Added OK");
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public ArrayList<WordEntry> getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<WordEntry> res = new ArrayList<WordEntry>();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor _res = db.rawQuery(query, null);

        if (_res.moveToFirst()) {
            do {
                int dictID = _res.getInt(_res.getColumnIndex(DICT_ID));
                String word = _res.getString(_res.getColumnIndex(WORD));
                String mean = _res.getString(_res.getColumnIndex(MEAN));
                WordEntry item = new WordEntry(word,mean,dictID);
                res.add(item);
            } while (_res.moveToNext());
            _res.close();
        }
        return res;
    }


    public ArrayList<WordEntry> getSugesstionList(String key) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<WordEntry> res = new ArrayList<WordEntry>();
        String [] selectionArgs = {key + "%"};
        String SELECT = new String("SELECT " + WORD+ " ," + MEAN  + " ," + DICT_ID + " FROM " + TABLE_NAME + " WHERE " + WORD +" LIKE ?");
        //Log.d("kei",SELECT);
        Cursor _res = db.rawQuery( SELECT,selectionArgs);

        if (_res.moveToFirst()) {
            do {
                int dictID = _res.getInt(_res.getColumnIndex(DICT_ID));
                String word = _res.getString(_res.getColumnIndex(WORD));
                String mean = _res.getString(_res.getColumnIndex(MEAN));
                WordEntry item = new WordEntry(word,mean,dictID);
                res.add(item);
            } while (_res.moveToNext());
            _res.close();
        }
        return res;
    }

}
