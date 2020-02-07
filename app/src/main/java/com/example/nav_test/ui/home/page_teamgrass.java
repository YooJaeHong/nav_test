package com.example.nav_test.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nav_test.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class page_teamgrass extends Fragment {



    String path;

    String[] all_file_string;

    int fileArray_length;
    Intent toTeamgrass = null;

    Context mContext;
    public page_teamgrass() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getContext()

        try {
            path = requireContext().getFilesDir().getPath();
            Log.e("output_file_path",path+"/team2.txt");
            OutputStream output = new FileOutputStream(path+"/team2.txt");
            OutputStream output2 = new FileOutputStream(path+"/team3.txt");
            String text ="temp";
            byte[] by = text.getBytes();
            Log.e("output_file_path",path+"/team2.txt");
            output.write(by);
            output2.write(by);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        toTeamgrass = new Intent(requireContext(), invidual_teamgrass.class);


        loadAllFile(path);

    }

    public void loadAllFile(String path){
        File file = new File(path);
        Log.e("file_path",path);

        File[] fileArray = file.listFiles();
        Log.e("file_num",Integer.toString(fileArray.length));
        if(fileArray != null) {
            fileArray_length = fileArray.length;
            all_file_string = new String[fileArray_length];
            for (int i = 0; i < fileArray_length; i++) {
                Log.e("filename",fileArray[i].getName());
                all_file_string[i] = fileArray[i].getName();
                //createlayout(fileArray[i].getName());
            }
        }
    }
    public LinearLayout createlayout(String teamname) {


        final LinearLayout teamgrass_block = new LinearLayout(mContext);
        final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, mContext.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width);

        //teamgrass_block.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        teamgrass_block.setGravity(Gravity.CENTER);

        teamgrass_block.setLayoutParams(params);


        final TextView Team_title = new TextView(mContext.getApplicationContext());

        Team_title.setGravity(Gravity.CENTER | Gravity.CENTER);
        Team_title.setText(teamname);
        //Team_title.setBackgroundResource(R.color.colorPrimaryDark);

        teamgrass_block.addView(Team_title);


        teamgrass_block.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.e("teamTitle.getText()", Team_title.getText().toString());
                toTeamgrass.putExtra("selected_team_name", Team_title.getText().toString());
                mContext.startActivity(toTeamgrass);
            }
        }

        );
        return teamgrass_block;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.page_teamgrass, container, false);

        for(int i=0;i<fileArray_length;i++){
            inflater.inflate(createlayout(all_file_string[i]),(LinearLayout)root.findViewById(R.id.teamgrass_outer));
        }

        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
