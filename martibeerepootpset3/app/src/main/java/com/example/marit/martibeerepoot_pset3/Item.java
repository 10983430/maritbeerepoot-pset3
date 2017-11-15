package com.example.marit.martibeerepoot_pset3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class Item extends AppCompatActivity {
    String category;
    String item;
    String image_url;
    String info;
    Drawable temp;
    public ArrayList<String> gerechten = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        category = (String) intent.getSerializableExtra("CategoryItem");
        item = (String) intent.getSerializableExtra("SelectedItem");
        TextView placeholder = (TextView) findViewById(R.id.placeholder);
        placeholder.setText(item);

        findViewById(R.id.button).setOnClickListener(new Click());

        getData();
    }


    public String getTextJSON(String string) throws JSONException, IOException {
        try {
            // The following code is from the android developers website
            JSONObject object = (JSONObject) new JSONTokener(string).nextValue();
            JSONArray cat = object.getJSONArray("items");
            // Loop through JSONArray to add the items to an Arraylist
            TextView priceholder = (TextView) findViewById(R.id.price);
            TextView desholder = (TextView) findViewById(R.id.description);
            for (int i = 0; i < cat.length(); i++) {
                if(Objects.equals(cat.getJSONObject(i).getString("name"), item)) {
                    desholder.setText(cat.getJSONObject(i).getString("description"));
                    priceholder.setText(cat.getJSONObject(i).getString("price"));
                    image_url = cat.getJSONObject(i).getString("image_url");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image_url;
    }

    public void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://resto.mprog.nl/menu";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ImageView imgview = (ImageView) findViewById(R.id.imageView);
                        TextView txt = (TextView) findViewById(R.id.textView6);
                        try {
                            info = getTextJSON(response);
                            txt.setText(info);
                            //temp = LoadImageFromWebOperations(info);
                            //imgview.setImageDrawable(temp);
                            Context context = getApplicationContext();
                            Picasso.with(context).load(info).into(imgview);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
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
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public class Click implements View.OnClickListener {
        public void onClick(View view) {
            TextView priceholder = (TextView) findViewById(R.id.price);
            String price = priceholder.getText().toString();
            TextView placeholder = (TextView) findViewById(R.id.placeholder);
            String name = placeholder.getText().toString();
            SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("string_id", InputString); //InputString: from the EditText
            editor.commit();
        }
    }
}
