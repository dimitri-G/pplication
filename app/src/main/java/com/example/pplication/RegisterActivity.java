package com.example.pplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //返回
        Button backward = findViewById(R.id.button_backward);
        backward.setOnClickListener(this);

        //注册
        Button register = findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_backward:
                finish();
                break;
            case R.id.register:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        postwebinfo();
                    }
                }).start();
                Intent toLoginMain = new Intent(RegisterActivity.this,LoginMainActivity.class);
                startActivity(toLoginMain);
                break;
            default:
                break;


        }
    }

    public String postwebinfo()
    {TextView nickname=findViewById(R.id.nickname);
        TextView password=findViewById(R.id.password);
        TextView gender=findViewById(R.id.sex);
        TextView age=findViewById(R.id.age);
        TextView address=findViewById(R.id.address);
        TextView telephone=findViewById(R.id.phone);
        TextView mail=findViewById(R.id.email);
        TextView height=findViewById(R.id.height);
        TextView weight=findViewById(R.id.weight);

        String str="";
        try {
            //1,找水源--创建URL
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/RegisterServlet");//放网站
            //URL url = new URL("http://192.168.91.1:8080/dyl/register");//放网站手机
            //URL url = new URL("http://"+MainActivity.URL_DOMAIN+":8080/dyl/buyticket");//放网站电脑
            //2,开水闸--openConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式
            conn.setRequestMethod("POST");
            //设置超时信息
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            //设置允许输入
            conn.setDoInput(true);
            //设置允许输出
            conn.setDoOutput(true);
            //post方式不能设置缓存，需手动设置为false
            //子线程中显示Toast
            /*
            conn.setUseCaches(false);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RegisterActivity.this, "id_cardans=\n"+id_cardans
                            +"\net_type=\n"+et_type.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                }
            });

             */
            //我们请求的数据


            String data = "nickname="+ URLEncoder.encode(nickname.getText().toString(),"UTF-8")
                    +"&password="+URLEncoder.encode(password.getText().toString(),"UTF-8")
                    +"&gender="+URLEncoder.encode(gender.getText().toString(),"UTF-8")
                    +"&age="+URLEncoder.encode(age.getText().toString(),"UTF-8")
                    +"&address="+URLEncoder.encode(address.getText().toString(),"UTF-8")
                    +"&telephone="+URLEncoder.encode(telephone.getText().toString(),"UTF-8")
                    +"&mail="+URLEncoder.encode(mail.getText().toString(),"UTF-8")
                    +"&height="+URLEncoder.encode(height.getText().toString(),"UTF-8")
                    +"&weight="+URLEncoder.encode(weight.getText().toString(),"UTF-8");
            //獲取輸出流
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();

            if (conn.getResponseCode() == 200||1==1) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "注册成功",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                // 释放资源
                Message msg = new Message();
                msg.what = 1;
                msg.obj = message.toString();
                Log.e("WangJ", message.toString());

            }
            else
            {
                conn.setUseCaches(false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "网络错误",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return str;
        }
    }


}
