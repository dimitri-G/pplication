package com.example.pplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.Shop;
import com.example.entity.Userdetails;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class PassLoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passlogin);

        //返回
        Button backward = findViewById(R.id.button_backward);
        backward.setOnClickListener(this);

        //注册
        Button register = findViewById(R.id.register);
        register.setOnClickListener(this);

        //登录
        Button login = findViewById(R.id.login);
        login.setOnClickListener(this);
    }
Handler handler;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_backward:
                finish();
                break;
            case R.id.register:
                Intent toRegister = new Intent(PassLoginActivity.this,RegisterActivity.class);
                startActivity(toRegister);
                break;
            case R.id.login:



                try{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String a=postwebinfo();
                            Message msg = handler.obtainMessage();
                            msg.what = 1;
                            msg.obj = a;
                            handler.sendMessage(msg);
                        }
                    }).start();

                    handler=new Handler(){
                        @Override
                        public void handleMessage(Message msg){
                            if(!msg.obj.toString().equals("ERROR"))
                            {Userdetails.getLoginUser(msg.obj.toString());
                            Intent login = new Intent(PassLoginActivity.this,MainActivity.class);
                            startActivity(login);
                            }
                            else
                                Toast.makeText(PassLoginActivity.this,"账户或密码错误",Toast.LENGTH_SHORT).show();
                        }
                    };
                }catch (Exception e){

                }

                break;
            default:
                break;
        }
    }
    public static String inputStream2String (InputStream in) throws IOException {

        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        int n;
        while ((n = in.read(b)) != -1) {
            out.append(new String(b, 0, n));
        }
        Log.i("String的长度", new Integer(out.length()).toString());
        return out.toString();
    }
    public String postwebinfo()
    {   TextView nickname=findViewById(R.id.nickname);
        TextView password=findViewById(R.id.password);
        String str="",a="";
        try {
            //1,找水源--创建URL
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/LoginServlet");//放网站
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
            //我们请求的数据
            String data = "nickname="+ URLEncoder.encode(nickname.getText().toString(),"UTF-8")
                    +"&password="+URLEncoder.encode(password.getText().toString(),"UTF-8");
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
                a = inputStream2String(is);
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                };
                 str=buffer.toString();

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
                        Toast.makeText(PassLoginActivity.this, "网络错误",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return a;
        }
    }

}
