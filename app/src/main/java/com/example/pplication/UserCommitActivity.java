package com.example.pplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.entity.Order;
import com.example.entity.Userdetails;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class UserCommitActivity extends AppCompatActivity implements View.OnClickListener {
    private AlertDialog alertDialog; //单选框
    private int ranking=0;//评分
    private EditText commit;
    private String flag="";
    String a=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_commit);
        Intent it=getIntent();
        a=it.getStringExtra("orderid");


        //Button btn_evlation=(Button)findViewById(R.id.btn_evalution);
        //btn_evlation.setOnClickListener(this);
        Button btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        commit =(EditText)findViewById(R.id.advice);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                if(ranking==0)
                    Toast.makeText(UserCommitActivity.this, "请先评分", Toast.LENGTH_SHORT).show();
                else if(commit.getText().toString().equals("")) {
                    Toast.makeText(UserCommitActivity.this, "评价不可为空", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        new Thread(new Runnable(){
                            @Override
                            public void run() {
                                flag=postwebinfo(a,commit.getText().toString());
                                }
                            }
                        ).start();
                        //while (flag.equals(""));
                        Toast.makeText(UserCommitActivity.this, "评价成功", Toast.LENGTH_SHORT).show();
                        Intent toOrderDetail =new Intent(UserCommitActivity.this,OrderDetailActivity.class);
                        toOrderDetail.putExtra("order",a);
                        startActivity(toOrderDetail);
                    }
                }
        }



    public void showSingleAlertDialog(View view) {
        final String[] items = {"1分", "2分", "3分", "4分","5分"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("评分");
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(UserCommitActivity.this, items[i], Toast.LENGTH_SHORT).show();
                ranking=i;
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog = alertBuilder.create();
        alertDialog.show();

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

    public String postwebinfo(String orderNum,String advice){
        String str="";String a = "";
        try {

            //1,找水源--创建URL
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/CommitServlet");//放网站
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

            String data = "orderNum="+ URLEncoder.encode(orderNum,"UTF-8")+
                    "&advice="+URLEncoder.encode(advice,"UTF-8")
                    +"&state="+URLEncoder.encode("7","UTF-8")
                    +"&ranking="+URLEncoder.encode(String.valueOf(ranking),"UTF-8");
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
