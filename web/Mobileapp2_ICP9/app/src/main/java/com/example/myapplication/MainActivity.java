package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.icu.number.NumberRangeFormatter;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    com.example.myapplication.Pizza Pizza;
    TextView total;
    double total_price;
    int quantity = 1;
    RadioButton r1;
    StringBuilder result=new StringBuilder();
    CheckBox chicken;
    CheckBox cheese;
    CheckBox corn;
    EditText firstNumberEditText;
    EditText EmailAddress;
    RadioGroup size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Pizza = new Pizza();
        total = findViewById(R.id.textView4);

        TextView firstText = (TextView) findViewById(R.id.textView);
        firstNumberEditText = (EditText) findViewById(R.id.editTextTextPersonName);
        EmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
        size = (RadioGroup) findViewById(R.id.radioGroup1);
        RadioButton small = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton medium = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton Large = (RadioButton) findViewById(R.id.radioButton4);
        chicken = (CheckBox) findViewById(R.id.checkBox);
        cheese = (CheckBox) findViewById(R.id.checkBox2);
        corn = (CheckBox) findViewById(R.id.checkBox3);
        Button summary = (Button) findViewById(R.id.button3);
        Button order = (Button) findViewById(R.id.button4);

        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int size_value = size.getCheckedRadioButtonId();
                if (size_value == -1)
                    Toast.makeText(MainActivity.this, "please select a size", Toast.LENGTH_SHORT).show();
                else{
                    r1 = findViewById(size_value);
                    Intent childIntent =new Intent(MainActivity.this, com.example.myapplication.order_summary.class);
                    childIntent.putExtra("name", firstNumberEditText.getText().toString());
                    childIntent.putExtra("size",r1.getText().toString());
                    childIntent.putExtra("topins", result.toString());
                    childIntent.putExtra("Total", total.getText().toString());
                    startActivity(childIntent);
                }
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("email",EmailAddress.getText().toString()+ "" + firstNumberEditText.getText().toString());
                composeEmail(EmailAddress.getText().toString().trim(), firstNumberEditText.getText().toString() );
            }
        });
    }
    public void radioClick (View view){
        boolean Checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton2:
                if (Checked)
                    Pizza.setPizza_size_price(3);
                break;
            case R.id.radioButton3:
                if (Checked)
                    Pizza.setPizza_size_price(5);
                break;
            case R.id.radioButton4:
                if (Checked)
                    Pizza.setPizza_size_price(8);
                break;
        }
        total.setText("Total Price:" + Pizza.getPizza_size_price() + "$" );
    }
    public void onCheckBoxClicked (View view){
        boolean Checked = ((CheckBox) view).isChecked();
        result.setLength(0);
        if (chicken.isChecked()) {
            Pizza.setChicken(5);
            result.append(chicken.getText().toString() + ", ");
        }
        else{
            Pizza.setChicken(0);
        }
        if (cheese.isChecked()) {
            Pizza.setCheese(3);
            result.append(cheese.getText().toString() + ", ");
        }
        else{
            Pizza.setCheese(0);
        }
        if (corn.isChecked()) {
            Pizza.setCorn(2);
            result.append(corn.getText().toString()+ ", ");
        }
        else{
            Pizza.setCorn(0);
        }
        int topin_length = result.length();
        if (topin_length>1){
            result.setLength(topin_length-2);
        }

        total.setText("Total Price:" + calculate_total() + "$");

    }

    private double calculate_total () {
        total_price = Pizza.getPizza_size_price() + Pizza.getChicken() + Pizza.getCheese() + Pizza.getCorn();
        TextView quantityTextView = (TextView) findViewById(R.id.textView3);
        quantityTextView.setText("" + quantity);
        return total_price;
    }
    public void increment (View view){
        if (quantity < 5) {
            quantity = quantity + 1;
            display(quantity);
            total.setText("Total Price:" + quantity * calculate_total());
        } else
            Toast.makeText(getApplicationContext(), "you have exceeded maximum no of items in cart", Toast.LENGTH_SHORT).show();
    }
    public void display ( int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.textView3);
        quantityTextView.setText("" + number);
    }


    public void decrement (View view){
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
            total.setText("Total Price:" + quantity * calculate_total());
        } else
            Toast.makeText(getApplicationContext(), "Please select at least one quantity", Toast.LENGTH_SHORT).show();
    }

    public void composeEmail (String addresses, String subject) {
        StringBuilder emailMessage = new StringBuilder();
        int size_value = size.getCheckedRadioButtonId();
        if (size_value == -1)
            Toast.makeText(MainActivity.this, "please select a size", Toast.LENGTH_SHORT).show();
        else {
            r1 = findViewById(size_value);
            Intent intent = new Intent(Intent.ACTION_SEND);
            //Log.i("compose email", addresses + "" + subject);
            Log.i("Send email", "");
            intent.setData(Uri.parse("mailto:"));
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL,new String[] {addresses});
            // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "your Pizza Order Details");
            emailMessage.append("Hi "+firstNumberEditText.getText().toString());
            emailMessage.append("\n your pizza size : "+r1.getText().toString());
            emailMessage.append("\n Topin's selected for your Pizza are : "+result.toString());
            intent.putExtra(Intent.EXTRA_TEXT, emailMessage.toString()+"\n Total Price is  " + quantity*  calculate_total());
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No app to send email. Please install at least one",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }
}

