package com.example.marit.martibeerepoot_pset3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PlacedOrder extends AppCompatActivity {
    String minutes;
    private BottomNavigationView mBottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);

        Intent intent = getIntent();
        minutes = (String) intent.getSerializableExtra("Minutes");
        TextView placeholder = (TextView) findViewById(R.id.textView3);
        placeholder.setText("Thanks for placing your order! Your order will take approximately " + minutes + " minutes to arrive!");

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.getMenu().findItem(R.id.Order).setChecked(true);
        mBottomNav.setOnNavigationItemSelectedListener(new navListener());
    }
        private class navListener implements BottomNavigationView.OnNavigationItemSelectedListener {
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
    }
    public void navMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
