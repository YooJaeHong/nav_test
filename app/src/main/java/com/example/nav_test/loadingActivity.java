package com.example.nav_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

public class loadingActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {

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
                github_parser date_color_parser = new github_parser();


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

                Intent main = new Intent(getBaseContext(),MainActivity.class);
                startActivity(main);
                finish();
            }
        },2000);
    }

}
