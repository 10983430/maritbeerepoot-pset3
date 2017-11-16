package com.example.marit.martibeerepoot_pset3;

import android.content.Intent;
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
import java.util.Objects;

public class Menu extends AppCompatActivity {
    String category;
    public ArrayList<String> info = new ArrayList<>();
    public ArrayList<String> gerechten = new ArrayList<>();
    private BottomNavigationView mBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getData();

        Intent intent = getIntent();
        category = (String) intent.getSerializableExtra("SelectedItem");
        TextView placeholder = (TextView) findViewById(R.id.placeholder);
        placeholder.setText(category);

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.getMenu().findItem(R.id.Menu).setChecked(true);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
        });
    }

    public void navOrder() {
        Intent intent = new Intent(this, Order.class);
        startActivity(intent);
    }

    public ArrayList<String> getTextJSON(String string) throws JSONException {
        try {
            // The following code is from the android developers website
            JSONObject object = (JSONObject) new JSONTokener(string).nextValue();
            JSONArray cat = object.getJSONArray("items");
            // Loop through JSONArray to add the items to an Arraylist
            for (int i = 0; i < cat.length(); i++) {
                if(Objects.equals(cat.getJSONObject(i).getString("category"), category)) {
                    gerechten.add(cat.getJSONObject(i).getString("name"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gerechten;
    }

    public void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://resto.mprog.nl/menu";
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
        final ListView theListView = (ListView) findViewById(R.id.menulist);
        theListView.setAdapter(theAdapter);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = "You selected " + String.valueOf(theListView.getItemAtPosition(position));
                Toast.makeText(Menu.this, selected, Toast.LENGTH_SHORT).show();
                navItem(String.valueOf(theListView.getItemAtPosition(position)));
            }
        });
    }

    public void navItem(String selecteditem) {
        Intent intent = new Intent(this, Item.class);
        intent.putExtra("CategoryItem", category);
        intent.putExtra("SelectedItem", selecteditem);
        startActivity(intent);
    }
}
