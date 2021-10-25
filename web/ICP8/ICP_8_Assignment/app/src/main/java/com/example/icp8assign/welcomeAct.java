package com.example.icp8assign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class welcomeAct extends AppCompatActivity {

    private Button loBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        loBtn=(Button) findViewById(R.id.logout);
        loBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(welcomeAct.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
}