package com.example.marit.martibeerepoot_pset3;

import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Marit on 16-11-2017.
 */

public class InformationOrder implements Serializable {

    private String price;
    private String item;

    public InformationOrder(String item, String price) {
        this.price = price;
        this.item = item;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String toString() {
        return item + " - " + price;
    }

    public JSONObject toJsonObject () {
        JSONObject o = new JSONObject();
        try {
            o.put("price", price);
            o.put("item", item);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o;
    }
}
