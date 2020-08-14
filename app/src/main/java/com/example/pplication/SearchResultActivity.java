package com.example.pplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.entity.OrderAdapater;
import com.example.entity.Shop;
import com.example.entity.ShopAdapterR;
import com.example.shoppingcart.ShoppingCartActivity;

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

public class SearchResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private static List<Shop> shopList=new ArrayList<>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent it2=getIntent();
        final String keyword=it2.getStringExtra("234");
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                String a=postwebinfo(keyword);
                /*Looper.prepare();
                Toast.makeText(SearchResultActivity.this, a, Toast.LENGTH_SHORT).show();
                Looper.loop();

                String[] spite=a.split(",");
                Shop shop1=null;
                for(int i=0;i<(int)spite.length/4;i++)
                {
                    if(i==1){
                        Looper.prepare();
                        Toast.makeText(SearchResultActivity.this, spite[i], Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    int imageid=R.drawable.shop1;
                    
                    shop1 = new Shop(spite[i*4],spite[1+i*4],spite[2+i*4],spite[3+i*4],imageid);
                    shopList.add(shop1);
                    Looper.prepare();
                    Toast.makeText(SearchResultActivity.this, spite[i], Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }).start();*/
        Toast.makeText(SearchResultActivity.this, keyword, Toast.LENGTH_SHORT).show();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Shop shop2=new Shop();
        shopList=shop2.getshopList(keyword);
        while(shopList.isEmpty());
        ShopAdapterR adapter = new ShopAdapterR(shopList,this);
        recyclerView.setAdapter(adapter);
        adapter.setmOnItemClickListener(new ShopAdapterR.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int postion) {
                Intent intent = new Intent(SearchResultActivity.this, ShoppingCartActivity.class);
                intent.putExtra("123", SearchResultActivity.shopList.get(postion).getShopId());
                startActivity(intent);
                shopList.clear();
                finish();

            }
        });
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

    /*public void changeActivity(String shopId){
        Intent intent=new Intent(this, ShoppingCartActivity.class);
        intent.putExtra("123",shopId);
        startActivity(intent);
    }
    public void OnItemClick(View v) {
      changeActivity(shopList.get((Integer) v.getTag()).getShopId());
    }*/

    public String postwebinfo(String keyword){
        String str="";String a = "";
        try {

            //1,找水源--创建URL
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/SearchServlet");//放网站
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

            String data = "keyword="+ URLEncoder.encode(keyword,"UTF-8");
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
