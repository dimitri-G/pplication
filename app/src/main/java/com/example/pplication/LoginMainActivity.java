package com.example.pplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginMainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        //密码登录
        Button toLoginPass = findViewById(R.id.to_login_pass);
        toLoginPass.setOnClickListener(this);

        //返回
        Button backward = findViewById(R.id.button_backward);
        backward.setOnClickListener(this);

        //注册
        TextView register = findViewById(R.id.register);
        register.setOnClickListener(this);

        //登录
        Button login = findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.to_login_pass:
                Intent toLoginPass = new Intent(LoginMainActivity.this,PassLoginActivity.class);
                startActivity(toLoginPass);
                break;
            case R.id.button_backward:
                finish();
                break;
            case R.id.register:
                Intent toRegister = new Intent(LoginMainActivity.this,RegisterActivity.class);
                startActivity(toRegister);
                break;
            case R.id.login:
                finish();
                Intent login = new Intent(LoginMainActivity.this,MainActivity.class);
                startActivity(login);
                break;
            default:
                break;
        }
    }
}
