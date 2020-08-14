package com.example.entity;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.pplication.R;
import com.example.pplication.SearchResultActivity;
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

import static com.example.shoppingcart.GoodsItem.postwebinfo;

public class Shop {
    private String shopName;
    private String shopAddr;
    private int shopImageId;
    private String shopOthers;
    private String shopId;
    private String telephone;

    public Shop(String shopId,String shopName,String shopAddr,String shopOthers,int shopImageId){
        this.shopId=shopId;
        this.shopName = shopName;
        this.shopAddr = shopAddr;
        this.shopOthers = shopOthers;
        this.shopImageId = shopImageId;
    }

    public Shop(String shopName,String shopAddr,String _telephone,String _shopID){
        this.shopName = shopName;
        this.shopAddr = shopAddr;
        this.telephone=_telephone;
        this.shopImageId = R.drawable.shop1;
        this.shopId=_shopID;
    }

    public Shop(){}

    public String getTelephone() {
        return telephone;
    }

    public String getShopName() {
        return shopName;
    }
    public String getShopId() {
        return shopId;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public int getShopImageId() {
        return shopImageId;
    }

    public void setShopImageId(int shopImageId) {
        this.shopImageId = shopImageId;
    }

    public String getShopOthers() {
        return shopOthers;
    }

    public void setShopOthers(String shopOthers) {
        this.shopOthers = shopOthers;
    }

    private ArrayList<Shop> shopList;

    private  void initData(final String keyword){
        shopList = new ArrayList<>();
        String a=null;
        try
        {new Thread(new Runnable() {
            @Override
            public void run() {
                String a = postwebinfo(keyword);
                String[] spite = a.split(",");
                Shop shop1 = null;
                for (int i = 0; i < (int) spite.length / 4; i++) {

                    int imageid = R.drawable.shop1;
                    shop1 = new Shop(spite[i * 4], spite[1 + i * 4], spite[2 + i * 4], spite[3 + i * 4], imageid);
                    shopList.add(shop1);
                }
            }}).start();
        }catch (Exception e){ }
    }

    public  ArrayList<Shop> getshopList(String keyword){
        if(shopList==null){
            initData(keyword);
        }
        return shopList;
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
    public static String postwebinfo(String keyword){
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
