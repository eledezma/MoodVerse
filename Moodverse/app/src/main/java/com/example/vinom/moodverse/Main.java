package com.example.vinom.moodverse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class Main extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] moodArray = {"Angry", "Sad", "Happy", "Worried",
        "Anxious", "Grateful", "Confident", "Random"};

        final ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                moodArray);

        ListView list = (ListView) findViewById(R.id.theListView);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String moodPicked = "You selected " +
                        String.valueOf(adapter.getItem(position));

                if (position == 0) {
                    Intent intent;
                    intent = new Intent(Main.this, mVerse.class);
                    startActivity(intent);
                }

                Toast.makeText(Main.this, moodPicked, Toast.LENGTH_SHORT).show();


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
