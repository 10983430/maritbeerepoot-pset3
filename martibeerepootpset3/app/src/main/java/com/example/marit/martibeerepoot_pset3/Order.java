package com.example.marit.martibeerepoot_pset3;

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

public class Order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ArrayList<String> list = new ArrayList<String>(3);
        list.add("Alice");
        list.add("Bob");
        list.add("Charlie");

        ArrayList<String> hoi = new ArrayList<String>(3);
        hoi.add("Alice");
        hoi.add("Bob");
        hoi.add("Charlie");
        makelistview(list, hoi);



    }

    public void makelistview(ArrayList<String> info) {
        final ListAdapter theAdapter = new adapter(this, info);
        final ListView theListView = (ListView) findViewById(R.id.catList);
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

    public class data {
        public String price;
        public String name;

        public data(String price, String name) {
            this.price = price;
            this.name = name;
        }
    }
}



