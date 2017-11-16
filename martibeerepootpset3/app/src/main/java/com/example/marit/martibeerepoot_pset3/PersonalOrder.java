package com.example.marit.martibeerepoot_pset3;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

/**
 * Created by Marit on 16-11-2017.
 */

public class PersonalOrder {
    private static ArrayList<InformationOrder> orders = new ArrayList<>();

    public static void addOrder(InformationOrder order) {
        orders.add(order);
    }

    public static ArrayList<InformationOrder> getOrders() {
        return orders;
    }

    public static InformationOrder get(int index) {
        return orders.get(index);
    }

    public static int size() {
        return orders.size();
    }

    public static void setJson(String json) {
        if(json == null) { return;}
        Log.d("TEST", json);
        try {
            JSONArray array = new JSONArray(json);
            ArrayList<InformationOrder> list = new ArrayList<>();
            for(int i = 0; i < array.length(); i++){
                JSONObject o = (JSONObject) array.get(i);
                list.add(new InformationOrder(o.getString("item"), o.getString("price")));
            }
            orders = list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
