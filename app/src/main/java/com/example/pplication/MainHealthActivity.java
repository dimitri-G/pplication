package com.example.pplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.text.DecimalFormat;
import java.util.Calendar;

public class MainHealthActivity extends AppCompatActivity implements View.OnClickListener {
    TextView energy;
    TextView c;
    TextView d;
    TextView fat;
    Button date;
    Button setDate;
    Button getWeek;
    int year=0;
    int month;
    int day;
Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_health);

        energy=findViewById(R.id.energy);
        c=findViewById(R.id.c);
        d=findViewById(R.id.d);
        fat=findViewById(R.id.f);


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
                    String[] s=msg.obj.toString().split(",");
                    DecimalFormat df   = new DecimalFormat("######0.00");
                    energy.setText(s[0]+"kcal");
                    c.setText(s[1]+"g");
                    d.setText(s[2]+"g");
                    fat.setText(s[3]+"g");
                    //notify();
                }
            };
        }catch (Exception e){

        }

        date=findViewById(R.id.date);
        date.setOnClickListener(this);

        setDate=findViewById(R.id.getDate);
        setDate.setOnClickListener(this);

        getWeek=findViewById(R.id.week);
        getWeek.setOnClickListener(this);

    }

    private void showDialogPick(final Button timeText) {
        final StringBuffer time = new StringBuffer();
        //获取Calendar对象，用于获取当前时间
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //实例化TimePickerDialog对象
        /*final TimePickerDialog timePickerDialog = new TimePickerDialog(MainHealthActivity.this, new TimePickerDialog.OnTimeSetListener() {
            //选择完时间后会调用该回调函数
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.append(" "  + hourOfDay + ":" + minute);
                //设置TextView显示最终选择的时间
                timeText.setText(time);
            }
        }, hour, minute, true);*/
        //实例化DatePickerDialog对象
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainHealthActivity.this, new DatePickerDialog.OnDateSetListener() {
            //选择完日期后会调用该回调函数
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //因为monthOfYear会比实际月份少一月所以这边要加1
                time.append(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                month=monthOfYear+1;
                day=dayOfMonth;
                date.setText(year+"/"+month+"/"+day);
                //选择完日期后弹出选择时间对话框
                //timePickerDialog.show();
            }
        }, year, month, day);
        //弹出选择日期对话框
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date:
                showDialogPick(date);
            break;

            case R.id.getDate:
                if(year!=0)
                {Intent todate=new Intent(MainHealthActivity.this,HealthDetailActivity.class);
                String strmonth=String.valueOf(month);
                if(month<10)
                    strmonth="0"+strmonth;
                String strDay=String.valueOf(day);
                if(day<10)
                    strDay="0"+strDay;

                todate.putExtra("date",(year-2000)+":"+strmonth+":"+strDay);
                startActivity(todate);
                }
                else{
                    Toast.makeText(MainHealthActivity.this,"请选择日期",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.week:
                Intent toweek=new Intent(MainHealthActivity.this,HealthWeekActivity.class);
                startActivity(toweek);

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

    public String postwebinfo(){
        String str="";String a = "";
        try {

            //1,找水源--创建URL
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/GetAdviceServlet");//放网站
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

            String data = "nickname="+ URLEncoder.encode(Userdetails.getname(),"UTF-8");
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
