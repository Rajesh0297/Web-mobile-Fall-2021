package com.example.icp8assign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {


    private EditText PersonName;
    private EditText Password;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PersonName=(EditText)findViewById(R.id.personName);
        Password=(EditText)findViewById(R.id.password);
        btn=(Button) findViewById(R.id.lgBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(PersonName.getText().toString(),Password.getText().toString());
            }
        });

    }

    private void login(String personName,String Password){
        if((personName.equals("testing"))&&(Password.equals("testing"))){
             Intent intent =new Intent(MainActivity.this,welcomeAct.class);
             startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Invalid credentials",Toast.LENGTH_SHORT).show();
        }
    }
}