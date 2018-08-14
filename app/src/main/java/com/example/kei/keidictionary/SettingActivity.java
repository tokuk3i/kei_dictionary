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

    Button insertData,insertData2,insertData3,dlbtn;
    String URL ="https://dl.google.com/dl/androidjumper/mtp/4421500/AndroidFileTransfer.dmg";
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
                insertDataFromCSV("engjp");
                insertData.setEnabled(false);
            }
        });

        insertData2 = (Button)findViewById(R.id.insertDataBtn2);
        insertData2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("setting","insertdata2");
                insertDataFromCSV("jpeng");
                insertData2.setEnabled(false);
            }
        });

        insertData3 = (Button)findViewById(R.id.insertDataBtn3);
        insertData3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("setting","insertdata3");
                insertDataFromCSV("vnjp");
                insertData3.setEnabled(false);
            }
        });


        dlbtn = (Button)findViewById(R.id.dlbtn);
        dlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("setting","dlbtn");
                new DownloadTask(SettingActivity.this,URL);

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
            InputStream is;
            if (filepath.equals("engjp")== true){
                is = getResources().openRawResource(R.raw.engjp);
            }else if (filepath.equals("jpeng")==true){
                is = getResources().openRawResource(R.raw.jpeng);
            }else if (filepath.equals("vnjp")==true){
                Log.d("import","imported vnjp");
                is = getResources().openRawResource(R.raw.vnjp);
            }else{
                return;
            }

            BufferedReader buffer = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8")));
            String line = "";
//            FileReader file = new FileReader(filepath);
//            BufferedReader buffer = new BufferedReader(file);
            ContentValues contentValues = new ContentValues();
//            String line = "";
            db.beginTransaction();
            //Log.d("import","readfile");
            //Message.message(this.getApplicationContext(),filepath+" Import start!");
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
            handler.reindex();
            db.setTransactionSuccessful();
            db.endTransaction();
            Message.message(this.getApplicationContext(),filepath+" Import finished!");
            Log.d("import",filepath + "Added OK");
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
