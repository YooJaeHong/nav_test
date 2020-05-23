package com.example.nav_test;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class loadingActivity extends Activity {
    Context context;
    protected void onCreate(Bundle savedInstanceState) {
        this.context = this;



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        startLoading();
    }

    private void startLoading() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Elements dates = null;
                Context context;
                String myname = "";

                try{

                    BufferedReader br = new BufferedReader(new FileReader(getCacheDir()+"myname.txt"));
                    String readStr = "";
                    String str = null;
                    while(((str = br.readLine()) != null)){
                        myname += str;

                    }

                    br.close();

                    //Toast.makeText(this, readStr.substring(0, readStr.length()-1), Toast.LENGTH_SHORT).show();

                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }

                github_parser date_color_parser = new github_parser(myname);


                try {
                    dates = date_color_parser.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BufferedWriter bufferedWriter = null;
                try {


                    String path = getFilesDir().getPath();
                    //Log.e("save","1");
                    //dates = date_color_parser.execute().get();
                    Log.e("writing path",path);
                    bufferedWriter = new BufferedWriter(new FileWriter(path+"/myGrassData.txt")) ;
                    //Log.e("save","3");




                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (Element date : dates) {
                    String raw_date = date.attr("abs:data-date");
                    String raw_color = date.attr("abs:fill");

                    int color_idx = raw_color.indexOf("#");

                    String url_deleted_date = raw_date.substring(19);
                    String url_deleted_color = raw_color.substring(color_idx);

                    try {
                        bufferedWriter.write(url_deleted_date);
                        bufferedWriter.newLine();
                        bufferedWriter.write(url_deleted_color);
                        bufferedWriter.newLine();

                        // Log.e("url_deleted_date",url_deleted_date);
                        // Log.e("url_deleted_color",url_deleted_color);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                set_background_alarm();

                Intent main = new Intent(loadingActivity.this,MainActivity.class);
                startActivity(main);
                finish();

            }
        },2000);
    }

    private void set_background_alarm(){
        AlarmManager alarmManager;
        PendingIntent pendingIntent;

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent alarm_reciver_Intent = new Intent(loadingActivity.this,Alarm_Reciver.class);



        pendingIntent = PendingIntent.getBroadcast(loadingActivity.this,0,alarm_reciver_Intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String day = dayFormat.format(currentTime);
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
            Calendar current = Calendar.getInstance();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR,21);
            if(current.compareTo(cal) == -1){
                alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(2000),pendingIntent);
            }
            alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"alarm set after"+1+"second",Toast.LENGTH_LONG).show();

    }



}
