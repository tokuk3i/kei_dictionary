package com.example.kei.keidictionary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SearchView.OnQueryTextListener {

    SearchView searchView;
    ListView listView;
    TextView contentView;
    ListEntryAdapter entriesAdapter;
    ArrayList<WordEntry> entriesList;
    DataHandler handle;
    Date previousTick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("main","savedInstanceState");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        handle = DataHandler.getInstance(this.getApplicationContext());
//        WordEntry entry1 = new WordEntry("kei","xin chao",1);
        ////        WordEntry entry2 = new WordEntry("dung","nihao",2);
        ////        handle.addNewWord(entry1);
        ////        handle.addNewWord(entry2);
        //        //Cursor check  = handle.getData();
        //        //Log.d("main","num : "+check.getCount());


        previousTick = Calendar.getInstance().getTime();
        searchView = (SearchView)findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(this);

        listView = (ListView)findViewById(R.id.listsugview);

        contentView = (TextView) findViewById(R.id.contentview);
        contentView.setVisibility(View.INVISIBLE);


        entriesList = new ArrayList<WordEntry>();
        //entriesList = handle.getSugesstionList("ke");

        entriesAdapter = new ListEntryAdapter(this);
        entriesAdapter.setWordList(entriesList);

        listView.setAdapter(entriesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                WordEntry entry = (WordEntry) o;
                listView.setVisibility(View.INVISIBLE);
                contentView.setText(android.text.Html.fromHtml(entry.getMean(),Html.FROM_HTML_MODE_COMPACT));
                contentView.setVisibility(View.VISIBLE);
                //Toast.makeText(MainActivity.this, "Selected :" + " " + country, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            Log.d("main","onBackPressed0");
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Log.d("main","onBackPressed1");
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("main","onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("main","onOptionsItemSelected");
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d("main","action_settings");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d("main","onNavigationItemSelected");
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_send) {
            // Handle the camera action
            Log.d("main","nav_send");
        } else if (id == R.id.nav_setting) {
            Log.d("main","nav_dictionary");
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_history) {
            Log.d("main","nav_history");
            Intent intent = new Intent(this, HistoryActivity.class);
            //Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //finish();

        } else if (id == R.id.nav_search) {
            Log.d("main","nav_manage");

        } else if (id == R.id.nav_send) {
            Log.d("main","nav_send");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {


        listView.setVisibility(View.INVISIBLE);
        if(entriesList.size() == 0){
            Message.message(this.getApplicationContext(),"No entry found");
            contentView.setText("No entry found");
            //contentView.setText(android.text.Html.fromHtml(entry.getMean(),1));
        }else{
            WordEntry res = entriesList.get(0);
            //contentView.setText(res.getMean());
            contentView.setText(android.text.Html.fromHtml(res.getMean(), Html.FROM_HTML_MODE_COMPACT));
        }
        contentView.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        /*String text = newText;
        adapter.filter(text);
        */

//        long c1 = Calendar.getInstance().getTime().getTime();
//        long c2 = previousTick.getTime();
//        long check = c1-c2;
//        previousTick = Calendar.getInstance().getTime();
//        Log.d("main","number : "+c1 + "and "+ c2 + "WITH : "+ check);
//        if ( check < 300){
//            //Log.d("tick","here" + (Calendar.getInstance().getTime().getTime() - previousTick.getTime()));
//            //previousTick = Calendar.getInstance().getTime()
//            return false;
//        }

        if(newText.isEmpty()){
            entriesList.clear();
            contentView.setText("");
            entriesAdapter.setWordList(entriesList);
        }else{
            ArrayList<WordEntry> resFilter = filter(newText);
            if(resFilter.size() == 0){
                entriesList.clear();
                entriesList = handle.getSugesstionList(newText);
                entriesAdapter.setWordList(entriesList);
            }else{
                entriesAdapter.setWordList(resFilter);
            }
        }

        //entriesList = new ArrayList<WordEntry>(handle.getSugesstionList(newText));
        //Log.d("main","number : "+entriesList.size());
        entriesAdapter.notifyDataSetChanged();
        listView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.INVISIBLE);
        return false;
    }

    private ArrayList<WordEntry> filter(String key) {
        ArrayList<WordEntry> res = new ArrayList<WordEntry>();
        for(int i = 0;i < this.entriesList.size();i++){
            if(entriesList.get(i).getWord().startsWith(key)){
                res.add(entriesList.get(i));
            }
        }
        return res;
    }

}
