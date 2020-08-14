package com.example.pplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.Nur;
import com.example.entity.NurAdapter;
import com.example.entity.Userdetails;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class HealthDetailActivity extends AppCompatActivity {
    List<Nur> nurList=new ArrayList<>();
    Handler handler;
    TextView totalPercent;
    RatingBar total;
    TextView energyPercent;
    RatingBar energy;
    TextView cPercent;
    RatingBar c;
    TextView dPercent;
    RatingBar d;
    TextView fatPercent;
    RatingBar fat;
    NurAdapter nurAdapter;
    ListView lv;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_detail);

        Intent it=getIntent();
        date=it.getStringExtra("date");

        totalPercent=findViewById(R.id.totalPercent);
        total=findViewById(R.id.total);
        energyPercent=findViewById(R.id.energyPercent);
        energy=findViewById(R.id.energy);

        cPercent=findViewById(R.id.carbonPercent);
        c=findViewById(R.id.carbon);
        dPercent=findViewById(R.id.ProPercent);
        d=findViewById(R.id.Pro);
        fatPercent=findViewById(R.id.FatPercent);
        fat=findViewById(R.id.Fat);


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
                    if(!msg.obj.toString().equals("empty"))
                    {String[] spite=msg.obj.toString().split("#");
                    String[] spite1=spite[0].split(",");
                    energyPercent.setText(spite1[0]+"分");
                    energy.setRating(Float.parseFloat(spite1[0])/20);
                    cPercent.setText(spite1[1]+"分");
                    c.setRating(Float.parseFloat(spite1[1])/20);
                    dPercent.setText(spite1[2]+"分");
                    d.setRating(Float.parseFloat(spite1[2])/20);
                    fatPercent.setText(spite1[3]+"分");
                    fat.setRating(Float.parseFloat(spite1[3])/20);
                    total.setRating(Float.parseFloat(spite1[4])/20);
                    totalPercent.setText(spite1[4]+"分");

                    String[] spite2=spite[1].split(",");
                    for(int i=0;i<(int)spite2.length/3;i++)
                    {
                        Nur nur=new Nur(spite2[3*i],spite2[1+3*i],spite2[2+3*i]);
                        nurList.add(nur);
                        //final String ab=shop1.getShopName();

                    }
                    lv=(ListView)findViewById(R.id.infer123);
                    nurAdapter=new NurAdapter(HealthDetailActivity.this,R.layout.item_nutr,nurList);
                    lv.setAdapter(nurAdapter);
                }
                    else
                    {
                        Toast.makeText(HealthDetailActivity.this,"当天未下单",Toast.LENGTH_SHORT).show();
                    }
                }
            };
        }catch (Exception e){

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

    public String postwebinfo(){
        String str="";String a = "";
        try {

            //1,找水源--创建URL
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/HealthDetailServlet");//放网站
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



            //我们请求的数据

            String data = "nickname="+ URLEncoder.encode(Userdetails.getname(),"UTF-8")
                            +"&date="+ URLEncoder.encode(date,"UTF-8");
            //獲取輸出流
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();

            if (conn.getResponseCode() == 200 || 1 == 1) {
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
                }

                // 释放资源
                /*Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = a;
                handler.sendMessage(msg);
                Log.e("WangJ", message.toString());*/
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
