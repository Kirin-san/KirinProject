package com.example.kirinproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.parse.GetCallback;
import com.parse.ParseUser;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;
import com.parse.FindCallback;

import com.parse.ParseException;


import com.parse.SignUpCallback;

public class NewsList extends AppCompatActivity {


    // setting up the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.news_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.Logout){
            ParseUser.logOut();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);




        final ListView newsList = (ListView) findViewById(R.id.newsList);
        final ArrayList<String> news = new ArrayList<>();

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,news);




        // It is necessary to logout before getting data from the parse server
        //ParseUser.logOut();
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("News");
        query2.setLimit(20);
        //query.whereEqualTo("playerName", "Dan Stemkoski");
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    Toast.makeText(getApplicationContext(), "Oui!!!", Toast.LENGTH_SHORT).show();
                    if (objects.size()>0){
                        for (ParseObject currentNews: objects){
                            news.add(currentNews.getString("Title"));
                        }
                        newsList.setAdapter(arrayAdapter);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
