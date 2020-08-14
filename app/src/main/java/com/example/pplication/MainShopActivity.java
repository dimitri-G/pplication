package com.example.pplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.entity.Food;
import com.example.entity.FoodAdapter;
import com.example.entity.Shop;
import com.example.entity.ShopAdapter;

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

public class MainShopActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Food> foodList = new ArrayList<>();
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_shop);
        ListView listView = (ListView)super.findViewById(R.id.food);

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
                    String[] spite=msg.obj.toString().split(",");
                    for(int i=0;i<(int)spite.length/2;i++)
                    {
                        int imageid=R.drawable.food1;
                        switch (i%3+1){
                            case 1:imageid=R.drawable.food1;break;
                            case 2:imageid=R.drawable.food2;break;
                            case 3:imageid=R.drawable.food3;break;
                        }
                        int a=Integer.parseInt(spite[1+i*2]);
                        Food food1 = new Food(spite[0+i*2],a,imageid);
                       // Shop shop1 = new Shop(spite[0+i*4],spite[1+i*4],spite[2+i*4],spite[3+i*4],imageid);
                        foodList.add(food1);

                    }
                    BinderListData(foodList);
                }
            };
        }catch (Exception e){

        }

        Button but1=findViewById(R.id.confirm);
        but1.setOnClickListener(this);
        //initFood();
        //FoodAdapter adapter = new FoodAdapter(MainShopActivity.this,R.layout.food_item,foodList);
        //listView.setAdapter(adapter);

       // Button tql=findViewById(R.id.tql);
        //tql.setOnClickListener(this);
        Button search = findViewById(R.id.search);
        search.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food food = foodList.get(i);

                Toast.makeText(MainShopActivity.this, food.getFoodName()+"添加成功",
                        Toast.LENGTH_SHORT).show();


//                Toast.makeText(MainActivity.this, "shop", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search:
                Intent intent = new Intent(MainShopActivity.this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.confirm:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        postwebinfo2();
                    }
                }).start();
                        Toast.makeText(MainShopActivity.this, "下单成功",
                                Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(MainShopActivity.this,MainActivity.class);
                startActivity(intent2);
               break;
            default:
                break;
        }
    }
    public void BinderListData(List<Food> person)
    {
        //创建adapter对象
        //adapter=new ListViewAdapter(R.layout.item,this,person);
        //将Adapter绑定到listview中
        //istView.setAdapter(adapter);
        ListView listView = (ListView)super.findViewById(R.id.food);

        FoodAdapter adapter = new FoodAdapter(MainShopActivity.this,R.layout.food_item,foodList);
        listView.setAdapter(adapter);
    }
    private void initFood(){
        Food food1 = new Food("名字1",12,R.drawable.food1);
        foodList.add(food1);
        foodList.add(new Food("名字2",13,R.drawable.food2));
        foodList.add(new Food("名字3",13,R.drawable.food3));
        foodList.add(new Food("名字4",13,R.drawable.food1));
        foodList.add(new Food("名字5",13,R.drawable.food3));
        foodList.add(new Food("名字6",13,R.drawable.food2));
        foodList.add(new Food("名字7",13,R.drawable.food2));

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
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/BuyyingServlet");//放网站
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
            Intent it2=getIntent();
            String shopid=it2.getStringExtra("123");


            //我们请求的数据

            String data = "shopid="+ URLEncoder.encode(shopid,"UTF-8");
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
    public String postwebinfo2(){
        String str="";String a = "";
        try {

            //1,找水源--创建URL
            URL url = new URL("http://192.168.43.21:8080/TomcatTest/OrderServlet");//放网站
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
            Intent it2=getIntent();
            String shopid=it2.getStringExtra("123");


            //我们请求的数据

            String data = "shopid="+ URLEncoder.encode(shopid,"UTF-8");
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
