package com.example.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pplication.MainActivity;
import com.example.pplication.R;

public class CheckoutSuccessActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_success);
        Button button_return=(Button)findViewById(R.id.button_return);
        button_return.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        case R.id.button_return:
            Intent intent=new Intent(CheckoutSuccessActivity.this, MainActivity.class);
            startActivity(intent);
            break;
        default:
            break;
        }
    }
}
