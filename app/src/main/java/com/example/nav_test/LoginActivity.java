package com.example.nav_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText myname = findViewById(R.id.input_myname);

        Button login = findViewById(R.id.login);
        login.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                File file = new File(getCacheDir()+"myname.txt") ;
                file.delete();
                FileWriter fw = null ;
                String text = myname.getText().toString();
                try {
                    // open file.
                    fw = new FileWriter(file) ;

                    // write file.
                    fw.write(text) ;

                } catch (Exception e) {
                    e.printStackTrace() ;
                }

                // close file.
                if (fw != null) {
                    // catch Exception here or throw.
                    try {
                        fw.close() ;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Intent loading = new Intent(LoginActivity.this,loadingActivity.class);
                startActivity(loading);
                finish();
            }
        });
    }


}
