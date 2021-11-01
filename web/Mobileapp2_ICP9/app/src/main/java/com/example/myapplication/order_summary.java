package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class order_summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        TextView first1 = (TextView) findViewById(R.id.textView6);
        TextView pizza_size = (TextView) findViewById(R.id.textView7);
        TextView pizza_number = (TextView) findViewById(R.id.textView9);
        TextView topins= (TextView) findViewById(R.id.textView8);
        Intent intentFromHome = getIntent();
        if(intentFromHome.hasExtra("name")){
            String textEntered = intentFromHome.getStringExtra("name");
            first1.setText("Name: " +textEntered);
            String text_size = intentFromHome.getStringExtra("size");
            pizza_size.setText("Pizza selected is  : " +text_size);
            String n_total = intentFromHome.getStringExtra("Total");
            pizza_number.setText(" "+n_total);
            String topins_e = intentFromHome.getStringExtra("topins");
            topins.setText("No of Topins: " +topins_e);

        }
        Button home = (Button) findViewById(R.id.button7);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(order_summary.this, com.example.myapplication.MainActivity.class);
                startActivity(intent);
            }
        });

    }
}