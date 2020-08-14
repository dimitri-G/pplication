package com.example.pplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.entity.Userdetails;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        //登录
        ImageView toLoginMain = findViewById(R.id.to_login_main);
        toLoginMain.setOnClickListener(this);
        if(!Userdetails.getname().equals("ERROR"))
            toLoginMain.setImageResource(R.drawable.logsus);
        //外卖
        ImageView toShop = findViewById(R.id.shop);
        toShop.setOnClickListener(this);

        Button logout=findViewById(R.id.logout);
        logout.setOnClickListener(this);

        Button update=findViewById(R.id.updateUser);
        update.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.to_login_main:
                Intent toLoginMain = new Intent(MyActivity.this,LoginMainActivity.class);
                startActivity(toLoginMain);
                break;
            case R.id.shop:
                Intent toShop = new Intent(MyActivity.this,MainActivity.class);
                startActivity(toShop);
                break;

            case R.id.logout:
                Intent toMain=new Intent(MyActivity.this,MainActivity.class);
                Userdetails.logout();
                startActivity(toMain);
                finish();
                break;

            case R.id.updateUser:
                Intent toUpdate=new Intent(MyActivity.this,MainActivity.class);

                startActivity(toUpdate);
                finish();
                break;


            default:
                break;
        }
    }
}
