package com.example.kei.keidictionary;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class SettingActivity extends AppCompatActivity {

    Button insertData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        insertData = (Button)findViewById(R.id.insertDataBtn);
        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("setting","insertdata");
                insertDataFromCSV("raw/test.csv");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void insertDataFromCSV(String filepath){

        DataHandler handler = DataHandler.getInstance(this.getApplicationContext());
        SQLiteDatabase db = handler.getWritableDatabase();
        //Log.d("import","num : "+ handler.getData().getCount());
        try {
            InputStream is = getResources().openRawResource(R.raw.test);
            BufferedReader buffer = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8")));
            String line = "";
//            FileReader file = new FileReader(filepath);
//            BufferedReader buffer = new BufferedReader(file);
            ContentValues contentValues = new ContentValues();
//            String line = "";
            db.beginTransaction();
            //Log.d("import","readfile");
            while ((line = buffer.readLine()) != null) {

                //Log.d("import",line);
                String[] str = line.split(",", 3);  // defining 3 columns with null or blank field //values acceptance

                //Id, Company,Name,Pric

                String dict_id = str[0].toString();
                String word = str[1].toString();
                String mean = str[2].toString();
                //Log.e("data", name);
                contentValues.put(DataHandler.DICT_ID, dict_id);
                contentValues.put(DataHandler.WORD, word);
                contentValues.put(DataHandler.MEAN, mean);
                db.insert(DataHandler.TABLE_NAME, null, contentValues);

                //Log.d("import","imported OK");

            }
            db.setTransactionSuccessful();

            db.endTransaction();
            Message.message(this.getApplicationContext(),"Import finished!");
            Log.d("import","Added OK");
        }catch (SQLException e)
        {
            Log.e("Error",e.getMessage().toString());
        }
        catch (IOException e) {

            if (db.inTransaction())
                db.endTransaction();
            Dialog d = new Dialog(this);
            d.setTitle(e.getMessage().toString() + "first");
            d.show();
            // db.endTransaction();
        }
    }

}
