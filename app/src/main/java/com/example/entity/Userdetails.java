package com.example.entity;

import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pplication.MainActivity;
import com.example.pplication.PassLoginActivity;
import com.example.pplication.R;
import com.example.shoppingcart.GoodsItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Userdetails {

    public  String UserName="123";
    public  int state;//0无效，1激活
    public  double balance=0;

    public Userdetails(String _UserName,double _balance){
        UserName=_UserName;
        state=1;
        balance=_balance;
    }
    private static Userdetails loginUser;

    public static void logout(){
        loginUser=null;
    }

    public static String getname(){
        if(loginUser==null){
            return "ERROR";
        }
        return loginUser.UserName;
    };
    public static double getBalance(){
        if(loginUser==null){
            return -1;
        }
        return loginUser.balance;
    };

    public static Userdetails getLoginUser(String a){
        if(loginUser==null){
            initData(a);
        }
            return loginUser;
    };

    private static void initData(final String a){
        String b=null;
        try
        {new Thread(new Runnable() {
            @Override
            public void run() {
                String[] spite=a.split(",");
                loginUser=new Userdetails(spite[0],Double.parseDouble(spite[1]));
            }
        }).start();

        }catch (Exception e){ }
    }

    public static void Update(String _UserName, String _state, String _balance){
        loginUser.UserName=_UserName;
        loginUser.state= 1;
        loginUser.balance= Double.parseDouble(_balance);
    }

   /* public String postwebinfo()
    {
        String str="";
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

                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                };
                String A=buffer.toString();
                String[] sqlite=A.split(",");
                Userdetails user=new Userdetails(sqlite[0],Double.parseDouble(sqlite[1]));

                // 释放资源
                Message msg = new Message();
                msg.what = 1;
                msg.obj = message.toString();
                Log.e("WangJ", message.toString());

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return str;
        }
    }*/


}
