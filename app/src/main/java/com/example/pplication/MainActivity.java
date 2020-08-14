package com.example.pplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.entity.Shop;
import com.example.entity.ShopAdapter;
import com.example.entity.Userdetails;
import com.example.shoppingcart.ShoppingCartActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Shop> shopList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //我的
        ImageView my = findViewById(R.id.my);
        my.setOnClickListener(this);

        ImageView order=findViewById(R.id.order);
        order.setOnClickListener(this);

        ImageView liu =findViewById(R.id.liu);
        liu.setOnClickListener(this);

        ImageView he=findViewById(R.id.he);
        he.setOnClickListener(this);

        ImageView ju=findViewById(R.id.ju);
        ju.setOnClickListener(this);

        ImageView song=findViewById(R.id.song);
        song.setOnClickListener(this);
        ImageView health=findViewById(R.id.health);
        health.setOnClickListener(this);
        //搜索
        Button search = findViewById(R.id.search);
        search.setOnClickListener(this);
        ListView listView = (ListView)super.findViewById(R.id.allInfoOfMerchant);

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
                    for(int i=0;i<(int)spite.length/4;i++)
                    {
                        int imageid=R.drawable.shop1;
                        switch (i%5+1){
                            case 1:imageid=R.drawable.shop1;break;
                            case 2:imageid=R.drawable.shop2;break;
                            case 3:imageid=R.drawable.shop3;break;
                            case 4:imageid=R.drawable.shop4;break;
                            case 5:imageid=R.drawable.shop5;break;
                        }

                        Shop shop1 = new Shop(spite[0+i*4],spite[1+i*4],spite[2+i*4],spite[3+i*4],imageid);
                        shopList.add(shop1);
                        final String ab=shop1.getShopName();

                    }
                    BinderListData(shopList);
                }
            };
        }catch (Exception e){

        }
        //initShop();
        // ShopAdapter adapter = new ShopAdapter(MainActivity.this,R.layout.shop_item,shopList);
        // ListView listView = (ListView)findViewById(R.id.allInfoOfMerchant);
        //listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shop shop = shopList.get(i);
                Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
                intent.putExtra("123",shop.getShopId());
                startActivity(intent);
//                Toast.makeText(MainActivity.this, "shop", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void BinderListData(List<Shop> person)
    {
        //创建adapter对象
        //adapter=new ListViewAdapter(R.layout.item,this,person);
        //将Adapter绑定到listview中
        //istView.setAdapter(adapter);
        ListView listView = (ListView)super.findViewById(R.id.allInfoOfMerchant);
        ShopAdapter adapter = new ShopAdapter(MainActivity.this,R.layout.shop_item,shopList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.my:
                Intent intentToMy = new Intent(MainActivity.this,MyActivity.class);
                startActivity(intentToMy);
                break;
            case R.id.search:
                Intent toSearch = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(toSearch);
                break;

            case R.id.liu:
                Intent toMain = new Intent(MainActivity.this,SearchResultActivity.class);
                String a=null;
                //a=postwebinfo(keyword.getText().toString());
                String test="柳";
                //while (a==null);
                toMain.putExtra("234",test);
                startActivity(toMain);
                break;

            case R.id.he:
                Intent toMain1 = new Intent(MainActivity.this,SearchResultActivity.class);
                String a1=null;
                //a=postwebinfo(keyword.getText().toString());
                String test1="荷";
                //while (a==null);
                toMain1.putExtra("234",test1);
                startActivity(toMain1);
                break;

            case R.id.ju:
                Intent toMain2 = new Intent(MainActivity.this,SearchResultActivity.class);
                //while (a==null);
                toMain2.putExtra("234","菊");
                startActivity(toMain2);
                break;

            case R.id.song:
                Intent toMain3 = new Intent(MainActivity.this,SearchResultActivity.class);
                //while (a==null);
                toMain3.putExtra("234","松");
                startActivity(toMain3);
                break;


            case R.id.order:
                Intent toMainOrder = new Intent(MainActivity.this,MainOrderActivity.class);
                if(Userdetails.getname().equals("ERROR"))
                    Toast.makeText(MainActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                else
                {
                    toMainOrder.putExtra("nickname", Userdetails.getname());
                    startActivity(toMainOrder);
                }
                break;

            case R.id.health:
                Intent toHealth=new Intent(MainActivity.this,MainHealthActivity.class);
                if(Userdetails.getname().equals("ERROR"))
                    Toast.makeText(MainActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                else
                {
                    startActivity(toHealth);
                }

                break;
            default:
                break;
        }

    }

    Handler handler;

    private void initShop() {
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

                ;
            }};

        /*Shop shop2 = new Shop("shop2", "aab", "001", R.drawable.shop2);
        shopList.add(shop2);
        Shop shop3 = new Shop("shop3", "aac", "002", R.drawable.shop3);
        shopList.add(shop3);
        Shop shop4 = new Shop("shop4", "aad", "003", R.drawable.shop4);
        shopList.add(shop4);
        Shop shop5 = new Shop("shop5", "aae", "004", R.drawable.shop5);
        shopList.add(shop5);
        Shop shop6 = new Shop("shop1", "aaa", "000", R.drawable.shop1);
        shopList.add(shop6);
        Shop shop7 = new Shop("shop2", "aab", "001", R.drawable.shop2);
        shopList.add(shop7);
        Shop shop8 = new Shop("shop3", "aac", "002", R.drawable.shop3);
        shopList.add(shop8);
        Shop shop9 = new Shop("shop4", "aad", "003", R.drawable.shop4);
        shopList.add(shop9);
        Shop shop10 = new Shop("shop5", "aae", "004", R.drawable.shop5);
        shopList.add(shop10);
        Shop shop11 = new Shop("shop1", "aaa", "000", R.drawable.shop1);
        shopList.add(shop11);
        Shop shop12 = new Shop("shop2", "aab", "001", R.drawable.shop2);
        shopList.add(shop12);
        Shop shop13 = new Shop("shop3", "aac", "002", R.drawable.shop3);
        shopList.add(shop13);
        Shop shop14 = new Shop("shop4", "aad", "003", R.drawable.shop4);
        shopList.add(shop14);
        Shop shop15 = new Shop("shop5", "aae", "004", R.drawable.shop5);
        shopList.add(shop15);*/
    }

    ;



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
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/ShoppingServlet");//放网站
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

            String data = "nickname=";
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
/*package com.example.pplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.entity.Shop;
import com.example.entity.ShopAdapter;
import com.example.pplication.R;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Shop> shopList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //我的
        ImageView my = findViewById(R.id.my);
        my.setOnClickListener(this);

        //搜索
        Button search = findViewById(R.id.search);
        search.setOnClickListener(this);

        initShop();
        ShopAdapter adapter = new ShopAdapter(MainActivity.this,R.layout.shop_item,shopList);
        ListView listView = (ListView)findViewById(R.id.allInfoOfMerchant);
        listView.setAdapter(adapter);
        ImageView shop11 = findViewById(R.id.shop11);
        my.setOnClickListener(this);
        ImageView shop12 = findViewById(R.id.shop12);
        my.setOnClickListener(this);
        ImageView shop13= findViewById(R.id.shop13);
        my.setOnClickListener(this);
        ImageView shop14 = findViewById(R.id.shop14);
        my.setOnClickListener(this);
        ImageView shop15 = findViewById(R.id.shop15);
        my.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shop shop = shopList.get(i);
                Intent intent = new Intent(MainActivity.this,MainShopActivity.class);
                startActivity(intent);
//                Toast.makeText(MainActivity.this, "shop", Toast.LENGTH_SHORT).show();
            }
        });
    }
    Handler handler;
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.my:
                Intent intentToMy = new Intent(MainActivity.this,MyActivity.class);
                startActivity(intentToMy);
                break;
            case R.id.search:
                Intent toSearch = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(toSearch);
                break;
            case R.id.shop11:
                Intent intent = new Intent(MainActivity.this,MainShopActivity.class);
                startActivity(intent);
                break;
            case R.id.shop12:
                Intent intent2 = new Intent(MainActivity.this,MainShopActivity.class);
                startActivity(intent2);
                break;
            case R.id.shop13:
                Intent intent3 = new Intent(MainActivity.this,MainShopActivity.class);
                startActivity(intent3);
                break;
            case R.id.shop14:
                Intent intent4 = new Intent(MainActivity.this,MainShopActivity.class);
                startActivity(intent4);
                break;
            case R.id.shop15:
                Intent intent5 = new Intent(MainActivity.this,MainShopActivity.class);
                startActivity(intent5);
                break;
            default:
                break;
        }

    }



    private void initShop(){
         String a="";
        String str="";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {String a="";
                    //1,找水源--创建URL
                    URL url = new URL("http://10.115.33.59:8080/TomcatTest/ShoppingServlet");//放网站
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


                    //我们请求的数据

                    String data = "nickname=";
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

                        a=inputStream2String(is);
                        byte buffer[] = new byte[1024];
                        // 按照缓冲区的大小，循环读取
                        while ((len = is.read(buffer)) != -1) {
                            // 根据读取的长度写入到os对象中
                            message.write(buffer, 0, len);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "查询成功",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        // 释放资源
                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        msg.obj = a;
                        handler.sendMessage(msg);
                        Log.e("WangJ", message.toString());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        handler=new Handler(){
    @Override
    public void handleMessage(Message msg){
        String[] spite=msg.obj.toString().split(",");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "查询成功233",
                        Toast.LENGTH_SHORT).show();
            }
        });
        for(int i=0;i<(int)spite.length/3;i++)
        {
            int imageid=R.drawable.shop1;
            switch (i%5+1){
                case 1:imageid=R.drawable.shop1;break;
                case 2:imageid=R.drawable.shop2;break;
                case 3:imageid=R.drawable.shop3;break;
                case 4:imageid=R.drawable.shop4;break;
                case 5:imageid=R.drawable.shop5;break;
            }

            Shop shop1 = new Shop(Integer.parseInt(spite[0+i*4]),spite[1],spite[2],spite[3],imageid);
            shopList.add(shop1);
            final String ab=shop1.getShopName();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, ab,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
};


    }
    public static String inputStream2String (InputStream in) throws IOException {

        StringBuffer out = new StringBuffer();
        byte[]  b = new byte[4096];
        int n;
        while ((n = in.read(b))!= -1){
            out.append(new String(b,0,n));
        }
        Log.i("String的长度",new Integer(out.length()).toString());
        return  out.toString();
    }
}

   /* public String postwebinfo() {
        String str="";
        try {
            //1,找水源--创建URL
            URL url = new URL("http://10.115.32.75:8080/TomcatTest/ShoppingServlet");//放网站
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


            //我们请求的数据

            String data = "nickname=";
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

                str=inputStream2String(is);
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "查询成功",
                                Toast.LENGTH_SHORT).show();
                    }
                });
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