package com.example.marit.martibeerepoot_pset3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
    public ArrayList info = new ArrayList();
    public TextView test;

    public ArrayList getTextJSON (String string) throws JSONException {
        ArrayList<String> categories = new ArrayList<String>();
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
        final TextView test = (TextView) findViewById(R.id.textView);
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
                            test.setText(info.toString());
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                test.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //final TextView test = (TextView) findViewById(R.id.textView);
        getData();


    }
}
