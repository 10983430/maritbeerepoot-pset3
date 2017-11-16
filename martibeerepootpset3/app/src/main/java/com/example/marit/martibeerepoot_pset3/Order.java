package com.example.marit.martibeerepoot_pset3;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Context context = getApplicationContext();
        SharedPreferences preferences = getSharedPreferences("settings",Context.MODE_PRIVATE);
        String s = preferences.getString("list", null);
        PersonalOrder.setJson(s);

        ArrayList<String> list = new ArrayList<String>(3);
        list.add("Alice");
        list.add("Bob");
        list.add("Charlie");

        ArrayList<String> hoi = new ArrayList<String>(3);
        hoi.add("Alice");
        hoi.add("Bob");
        hoi.add("Charlie");
        makelistview(PersonalOrder.getOrders());

        //TextView priceholder = (TextView) findViewById(R.id.textView);
        //priceholder.setText(PersonalOrder.getOrders().stream().toString());
        //TextView placeholder = (TextView) findViewById(R.id.textView2);
        //String name = placeholder.getText().toString();

    }

    public <T> void makelistview(ArrayList<T> info) {
        final ListAdapter theAdapter = new ArrayAdapter<T>(this, android.R.layout.simple_list_item_1, info);
        final ListView theListView = findViewById(R.id.catList);
        theListView.setAdapter(theAdapter);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = "You selected " + String.valueOf(theListView.getItemAtPosition(position));
                Toast.makeText(Order.this, selected, Toast.LENGTH_SHORT).show();
                //navMenu(String.valueOf(theListView.getItemAtPosition(position)));
            }
        });
    }

}



