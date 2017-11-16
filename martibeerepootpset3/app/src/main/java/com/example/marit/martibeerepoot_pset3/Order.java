package com.example.marit.martibeerepoot_pset3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Order extends AppCompatActivity {
    public ArrayList<String> categories = new ArrayList<>();
    public ArrayList<String> item = new ArrayList<>();
    String lol;
    int size;
    private SharedPreferences preferences;
    private BottomNavigationView mBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

        Context context = getApplicationContext();
        SharedPreferences preferences = getSharedPreferences("settings",Context.MODE_PRIVATE);
        String s = preferences.getString("list", null);
        PersonalOrder.setJson(s);
        size = PersonalOrder.size();
        TextView count = (TextView) findViewById(R.id.textView5);
        count.setText("You currently have " + String.valueOf(size) + " items in your order.");

        makelistview(PersonalOrder.getOrders());

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.getMenu().findItem(R.id.Order).setChecked(true);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Menu:
                        navMenu();
                    case R.id.Order:
                        break;
                }
                return false;
            }
        });
    }
    public void navMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
                PersonalOrder.deleteItem(position);
                JSONArray array = new JSONArray();

                for(int i = 0; i < PersonalOrder.size(); i++) {
                    array.put(PersonalOrder.get(i).toJsonObject());
                }

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("list", array.toString());
                editor.apply();
                Context context = getApplicationContext();
                Intent intent = new Intent(context, Order.class);
                startActivity(intent);
                finish();

            }
        });
    }

    public void click(View v){
        getData();
        Toast.makeText(this, "Order placed", Toast.LENGTH_LONG).show();
    }

    public void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://resto.mprog.nl/order";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String temp = (jsonObj.getString("preparation_time"));
                            navPlaced(temp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //test.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);
    }

    public void navPlaced(String extra){
        lol = extra;
        Log.d("TEST",lol);
        Intent intent = new Intent(this, PlacedOrder.class);
        intent.putExtra("Minutes", lol);
        startActivity(intent);
    }

}



