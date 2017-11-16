package com.example.marit.martibeerepoot_pset3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.marit.martibeerepoot_pset3.R;

import java.util.ArrayList;

class adapter extends ArrayAdapter<String> {
    public adapter(@NonNull Context context, ArrayList<String> values) {
        super(context, R.layout.rowlayout, values);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater theInflator = LayoutInflater.from(getContext());
        View theView  = theInflator.inflate(R.layout.rowlayout, parent, false);
        String item = getItem(position);
        TextView theTextView = theView.findViewById(R.id.textView1);
        theTextView.setText(item);
        return theView;
    }
}