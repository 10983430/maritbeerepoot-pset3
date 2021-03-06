package com.example.marit.martibeerepoot_pset3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> info = new ArrayList<>();
    public ArrayList<String> categories = new ArrayList<>();
    private BottomNavigationView mBottomNav;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();

        Context context = getApplicationContext();
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        String s = preferences.getString("list", null);
        PersonalOrder.setJson(s);

        size = PersonalOrder.size();
        TextView count = (TextView) findViewById(R.id.count);
        count.setText("You currently have " + String.valueOf(size) + " items in your order.");

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.getMenu().findItem(R.id.Menu).setChecked(true);
        mBottomNav.setOnNavigationItemSelectedListener(new navListener());
    }

    private class navListener implements BottomNavigationView.OnNavigationItemSelectedListener {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Menu:
                        break;
                    case R.id.Order:
                        navOrder();
                }
                return false;
            }
    }

    // This function makes sure the right count of items in the order is displayed, when back navigation is used
    public void onRestart() {
        super.onRestart();

        size = PersonalOrder.size();
        TextView count = (TextView) findViewById(R.id.count);
        count.setText("You currently have " + String.valueOf(size) + " items in your order.");
    }

    public ArrayList<String> getTextJSON(String string) throws JSONException {
        try {
            // The following code is from the android developers website
            JSONObject object = (JSONObject) new JSONTokener(string).nextValue();
            JSONArray cat = object.getJSONArray("categories");
            // Loop through JSONArray to add the items to an Arraylist
            for (int i = 0; i < cat.length(); i++) {
                categories.add(cat.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://resto.mprog.nl/categories";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            makelistview(getTextJSON(response));
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

    public void makelistview(ArrayList<String> info) {
        final ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, info);
        final ListView theListView = (ListView) findViewById(R.id.catList);
        theListView.setAdapter(theAdapter);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = "You selected " + String.valueOf(theListView.getItemAtPosition(position));
                Toast.makeText(MainActivity.this, selected, Toast.LENGTH_SHORT).show();
                navMenu(String.valueOf(theListView.getItemAtPosition(position)));
            }
        });
    }

    public void navOrder() {
        Intent intent = new Intent(this, Order.class);
        startActivity(intent);
    }

    public void navMenu(String selecteditem) {
        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("SelectedItem", selecteditem);
        startActivity(intent);
    }

}
