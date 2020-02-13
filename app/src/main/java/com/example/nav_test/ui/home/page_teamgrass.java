package com.example.nav_test.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.nav_test.R;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
    Intent toInputgrass = null;

    Context mContext;
    public page_teamgrass() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this.getContext();


            path = requireContext().getFilesDir().getPath()+File.separator+"teamname";
            File teamname_dir = new File(path);
            if(!teamname_dir.exists())
                teamname_dir.mkdirs();

        toTeamgrass = new Intent(requireContext(), invidual_teamgrass.class);

        toInputgrass = new Intent(requireContext(),input_teamgrass.class);




        loadAllFile(path);

    }

    public void loadAllFile(String path){
        File file = new File(path);
        Log.e("file_path",path);

        File[] fileArray = file.listFiles();
        Log.e("Load_all_Files_amount",Integer.toString(fileArray.length));
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
        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, mContext.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);

        //teamgrass_block.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        teamgrass_block.setGravity(Gravity.CENTER);

        teamgrass_block.setLayoutParams(params);


        final TextView Team_title = new TextView(mContext.getApplicationContext());

        Team_title.setGravity(Gravity.CENTER | Gravity.CENTER);

        String txt_removed_teamname = teamname.substring(0, teamname.lastIndexOf("."));
        Team_title.setText(txt_removed_teamname);
        //Team_title.setBackgroundResource(R.color.colorPrimaryDark);

        teamgrass_block.addView(Team_title);


        teamgrass_block.setOnClickListener(new View.OnClickListener() {
                                               @Override

                                               public void onClick(View view) {

                                                   Bundle args = new Bundle();
                                                   args.putString("selected_team_name", Team_title.getText().toString());
                                                   Fragment fragment = new invidual_teamgrass();
                                                   fragment.setArguments(args);
                                                   FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                   FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                   fragmentTransaction.replace(R.id.drawer_layout, fragment);
                                                   fragmentTransaction.addToBackStack(null);

                                                   fragmentTransaction.commit();

                                               }
                                           }
        );
return teamgrass_block;
    }
            /*    Log.e("teamTitle.getText()", Team_title.getText().toString());
                toTeamgrass.putExtra("selected_team_name", Team_title.getText().toString());
                mContext.startActivity(toTeamgrass);
                fab.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               Fragment fragment = new input_teamgrass();
                                               FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                               fragmentTransaction.replace(R.id.drawer_layout,fragment);
                                               fragmentTransaction.addToBackStack(null);
                                               fragmentTransaction.commit();




                                           }
        }

        );
        return teamgrass_block;
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.page_teamgrass, container, false);

        Log.e("file_length",Integer.toString(fileArray_length));
        for(int i=0;i<fileArray_length;i++){
            Log.e("block_name",all_file_string[i]);
            ((LinearLayout)root.findViewById(R.id.teamgrass_scrollview_layout)).addView(createlayout(all_file_string[i]));
            Log.e("each block","created");

        }
        Log.e("all block","created");
        //((LinearLayout)root.findViewById(R.id.teamgrass_scrollview_layout)).setLayoutParams(new ScrollView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        //((ScrollView)root.findViewById(R.id.teamgrass_scrollview));//setLayoutParams(new LinearLayoutCompat.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT));
        //root.findViewById(R.id.teamgrass_scrollview).invalidate();

        FloatingActionButton fab = root.findViewById(R.id.button_on_teamgrass);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new input_teamgrass();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.drawer_layout,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });//버블
        //((LinearLayout)root.findViewById(R.id.teamgrass_scrollview_layout)).addView(fab);
        Log.e("bubble","created");
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
