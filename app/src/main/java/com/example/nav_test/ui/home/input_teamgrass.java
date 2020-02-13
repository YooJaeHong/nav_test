package com.example.nav_test.ui.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.nav_test.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class input_teamgrass extends Fragment {
    Context context = getContext();

    EditText ed = null;
    final List<EditText> allEds = new ArrayList<EditText>();


    View root = null;

    int num_of_text = 1;


    public input_teamgrass() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_input_teamgrass, container, false);

        final LinearLayoutCompat outer = root.findViewById(R.id.text_container);
        final EditText input_teamname = root.findViewById(R.id.input_teamname_text);
        Button member_plus_button = root.findViewById(R.id.member_plus_btn);
        Button confirm = root.findViewById(R.id.create_btn);


        EditText ed = new EditText(requireContext());
        ed.setHint("팀원 아이디");
        ed.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        allEds.add(ed);
        outer.addView(ed);



        member_plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed = new EditText(requireContext());
                ed.setHint("팀원 아이디");


                ed.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                allEds.add(ed);
                outer.addView(ed);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path ="";
                String teamname = input_teamname.getText().toString();
                try {
                    path = requireContext().getFilesDir().getPath()+ File.separator+"teamname";
                    File teamname_dir = new File(path);
                    if(!teamname_dir.exists())
                        teamname_dir.mkdirs();




                    Log.e("output_file_path",path+"/"+teamname+".txt");
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path+"/"+teamname+".txt"));
                    // output2 = new FileOutputStream(path+"/team3.txt");
                    for(int i=0;i<allEds.size();i++){
                        String member = allEds.get(i).getText().toString();
                        Log.e("writed id", member);
                        bufferedWriter.write(member);
                        bufferedWriter.newLine();
                        //bufferedWriter.newLine();
                    }
                    bufferedWriter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });




        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
