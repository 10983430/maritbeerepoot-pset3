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
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> info = new ArrayList<String>();
    public ArrayList<String> categories = new ArrayList<String>();
    //public TextView test;

    public ArrayList<String> getTextJSON (String string) throws JSONException {
        //ArrayList<String> categories = new ArrayList<String>();
        try {
            // The following code is from the android developers website
            JSONObject object = (JSONObject) new JSONTokener(string).nextValue();
            JSONArray cat = object.getJSONArray("categories");
            // Loop through JSONArray to add the items to an Arraylist
            for (int i = 0; i < cat.length(); i++) {
                categories.add(cat.get(i).toString());
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return categories;
    }

    public void getData(){
        //final TextView test = (TextView) findViewById(R.id.textView);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://resto.mprog.nl/categories";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // mTextView.setText("Response is: " + response);
                        try {
                            info = getTextJSON(response);
                            //test.setText(categories.toString());
                            makelistview();
                        }

                        catch (JSONException e) {
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
        System.out.println(info);
    }

    public void makelistview() {
        final ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, info);
        final ListView theListView = (ListView) findViewById(R.id.catList);
        theListView.setAdapter(theAdapter);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = "You selected " + String.valueOf(theListView.getItemAtPosition(position));

                Toast.makeText(MainActivity.this, selected, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();

        final TextView test = (TextView) findViewById(R.id.textView);
        //test.setText(categories.get(0));

        String[] lol = {"1232", "kjsdfljsdlf"};
        /*final ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, info);
        final ListView theListView = (ListView) findViewById(R.id.catList);
        theListView.setAdapter(theAdapter);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = "You selected " + String.valueOf(theListView.getItemAtPosition(position));

                Toast.makeText(MainActivity.this, selected, Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
