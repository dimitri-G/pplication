package com.example.shoppingcart;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

public class GoodsItem{
    public int id;
    public int typeId;
    public int rating;
    public String name;
    public String typeName;
    public double price;
    public int count;
    public String realId;
    public String foodNur;
    public String foodCon;
    public GoodsItem(){}

    public GoodsItem(int id, String realId,double price, String name, int typeId, String typeName,String foodCon,String nur) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.typeId = typeId;
        this.typeName = typeName;
        this.realId=realId;
        this.foodCon=foodCon;
        foodNur=nur;
        rating = new Random().nextInt(1)+4;
    }

    public String getRealId() {
        return realId;
    }

    private  ArrayList<GoodsItem> goodsList;
    private  ArrayList<GoodsItem> typeList;

    private  void initData(final String shopid){
        goodsList = new ArrayList<>();
        typeList = new ArrayList<>();
        String a=null;

        try
        {new Thread(new Runnable() {
            @Override
            public void run() {
                String a=postwebinfo(shopid);
                String[] spite=a.split(",");
                GoodsItem item = null;
                int typeid=0;
                int flag=0;
                int goodid=0;
                for(int i = 0;i < (int)spite.length/8; i++){
                    int price=Integer.parseInt(spite[1+i*8]);
                    typeid=Integer.parseInt(spite[2+i*8]);
                    goodid=Integer.parseInt(spite[4+i*8]);
                    item = new GoodsItem(goodid,spite[5+i*8],price,spite[8*i],typeid,spite[8*i+3],spite[8*i+6],spite[8*i+7]);
                    goodsList.add(item);
                    if(flag!=typeid)
                    {
                        typeList.add(item);
                        flag=typeid;
                    }
                }
            }
        }).start();

        }catch (Exception e){ }
    }

    public  ArrayList<GoodsItem> getGoodsList(String shopid){
        if(goodsList==null){
            initData(shopid);
        }
        return goodsList;
    }
    public  ArrayList<GoodsItem> getTypeList(String shopid){
        if(typeList==null){
            initData(shopid);
        }
        return typeList;
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

    public static String postwebinfo(String shopid){
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
