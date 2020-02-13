package com.example.nav_test.ui.home;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.nav_test.R;
import com.example.nav_test.github_api_parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;




public class invidual_teamgrass extends Fragment {

    Context mContext;

    HashMap<String,Integer> date_user_contributions;
    HashMap<String, int[][]> user_commitList;


    String path;
    String which_block;


    String [] commits_week;
    int[][] commits_data;

    LinkedList<Integer> commit_array= new LinkedList<>();
    int max_of_commit_array = 0;
    LinkedList<String> name_array = new LinkedList<>();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getContext();
        which_block =getArguments().getString("selected_team_name");
        for(int i=0;i<52*7;i++){
            commit_array.add(0);
        }
    }
    @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.activity_invidual_teamgrass, container, false);
















        path = requireContext().getFilesDir().getPath()+File.separator+"teamname";
        /*Intent intent = getActivity().getIntent();
        String which_block = intent.getExtras().getString("selected_team_name");*/
        Log.e("received by Teamgrass",which_block);

        File file = new File(path+"/"+which_block+".txt");
        Log.e("file_path", file.getPath());

        HashMap<String,Integer> date_name_contributions = new HashMap<>();
        HashMap<String,Integer[][]> uesr_commitList=  new HashMap<>();



        try {
            FileReader filereader = new FileReader(file);
            BufferedReader bufreader = new BufferedReader(filereader);
            String line = "";

            while((line = bufreader.readLine()) != null){
                name_array.add(line);

                Log.e("registered id", name_array.getLast());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




        for(int i=0;i<name_array.size();i++) {

            github_api_parser user_parser = new github_api_parser("https://api.github.com/users/" + name_array.get(i)+"/repos");
            Log.e("new parser","created");
            try {

                String receiveMsg = user_parser.execute().get();


                JSONArray repos_array = new JSONArray(receiveMsg);
                String[] repos_url = new String[repos_array.length()];
                Log.e("repos_array.length()",Integer.toString(repos_array.length()));

                for(int j=0;j<repos_url.length;j++){
                    JSONObject each_repo = repos_array.getJSONObject(j);
                    repos_url[j] = each_repo.optString("url");
                    github_api_parser repos_parser = new github_api_parser(repos_url[j]+"/stats/commit_activity");

                    String commits_message = repos_parser.execute().get();
                    if(commits_message != null) {
                        Log.e("commit_message",commits_message);

                        JSONArray commits_array = new JSONArray(commits_message);

                        //https://api.github.com/repos/YooJaeHong/android_grass/commits
                        commits_week = new String[commits_array.length()];
                        commits_data = new int[commits_array.length()][7];

                        for (int k = 0; k < commits_week.length; k++) {
                            JSONObject each_week = commits_array.getJSONObject(k);//가장 바깥
                            //commits_week[k] = each_week.optString("week");//커밋시간 알아내기:->필요없겠다
                            //Log.e("week", commits_week[k]);

                            JSONArray days = each_week.getJSONArray("days");
                            for (int l = 0; l < 7; l++) {
                                //commits_data[k][l] = days.getInt(l);
                                int arrays_num  = commit_array.get(l+k*7);
                                arrays_num +=days.getInt(l);
                                Log.e("arrays_num",Integer.toString(arrays_num));
                                commit_array.set(l+k*7,arrays_num);
                                if(arrays_num>max_of_commit_array){
                                    max_of_commit_array = arrays_num;
                                }

                            }
                        }
                    }
                    //user_commitList.put(name_array.get(i),commits_data);//유저이름과 커밋리스트 연동
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RecyclerView recyclerView = root.findViewById(R.id.teamgrass_calendar);
            recyclerView.setLayoutManager(new GridLayoutManager(this));

            recycler_view_adapter adapter = new recycler_view_adapter(commit_array);//




            //scroll.addView(createHorizonialLayout());

            /*String repos[] = new String[user_parser.get_repos_url_size()];
            repos = user_parser.get_repos_url();
            for(int j = 0;j<repos.length;j++){
                github_api_parser repos_parser = new github_api_parser(repos[j]);
                repos_parser.execute();
                repos_parser.set_commit_URL();
                String commits[] = new String[repos_parser.get_comment_url_size()];
                commits = repos_parser.get_commit_URL();
                for(int k=0;k<commits.length;k++){
                    github_api_parser commit_parser = new github_api_parser(commits[k]);
                    commit_parser.execute();
                    commit_parser.set_addDel_date_committer();
                    String date_name = commit_parser.get_Date()+"_"+commit_parser.get_name();

                    if(commit_parser.get_name().equals(name_array.get(i))){
                        if(date_name_contributions == null) {
                            date_name_contributions.put(date_name,1);
                        }
                        else {
                            date_name_contributions.put(date_name, date_name_contributions.get(date_name) + 1);
                        }
                        Log.e("date_name_contribution", String.valueOf(date_name_contributions.get(date_name)));


                    }
                    //https://www.techiedelight.com/implement-map-with-multiple-keys-multikeymap-java/
                    // commit마다 날짜, 변경갰수, commit 한사람 return 가능
                }
            }*/

        }//name_array for문 종료


/*
        github_parser date_color_parser = new github_parser(name_array.pop());
        Elements dates = null;

        try {
            dates = date_color_parser.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
*/
        return root;
    }

    int col_num=3;
    int row_num=3;


    /*public LinearLayout createHorizonialLayout(){
        final LinearLayout all_day_layout = new LinearLayout(mContext);
        all_day_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        all_day_layout.setOrientation(LinearLayout.HORIZONTAL);
        for(int i=0;i<col_num;i++){
            all_day_layout.addView(createVerticalLayout(),all_day_layout.getChildCount()-1);
            Log.e("verticalLayout","created");
        }


        return all_day_layout;
    }

    public LinearLayout createVerticalLayout(){
        final LinearLayout week_layout =new LinearLayout(mContext);
        week_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        week_layout.setOrientation(LinearLayout.VERTICAL);
        for(int i=0;i<row_num;i++) {
            week_layout.addView(createFrame(commit_array.pop(), max_of_commit_array),week_layout.getChildCount()-1);
            Log.e("frame","created");
        }
        return week_layout;
    }*/






    public void createFrame(int commit_num, int max/*,String date*/) {

        ArrayList<String> colorList = new ArrayList<>();
        float commitNum_devide_max = (float)commit_num/(float)max;
        if(commitNum_devide_max == 0){
            colorList.add("#ebedf0");
        }
        else if(commitNum_devide_max<0.25){
            colorList.add("#c6e48b");
        }
        else if(commitNum_devide_max<0.5){
            colorList.add("#7bc96f");
        }
        else if(commitNum_devide_max<0.75){
            colorList.add("#239a3b");
        }
        else if(commitNum_devide_max<=1){
            colorList.add("#196127");
        }
        else{
            Log.e("devide error!", String.valueOf(commitNum_devide_max));
        }



        /*final FrameLayout teamgrass_block = new FrameLayout(mContext);
        final TextView day_block = new TextView(mContext);
        //if today
        //teamgrass_block.setBackgroundColor(blue);
        //
        day_block.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));
        /*float commitNum_devide_max = (float)commit_num/(float)max;
        if(commitNum_devide_max == 0){
            day_block.setBackgroundColor(Color.parseColor("#ebedf0"));
        }
        else if(commitNum_devide_max<0.25){
            day_block.setBackgroundColor(Color.parseColor("#c6e48b"));
        }
        else if(commitNum_devide_max<0.5){
            day_block.setBackgroundColor(Color.parseColor("#7bc96f"));
        }
        else if(commitNum_devide_max<0.75){
            day_block.setBackgroundColor(Color.parseColor("#239a3b"));
        }
        else if(commitNum_devide_max<=1){
            day_block.setBackgroundColor(Color.parseColor("#196127"));
        }
        else{
            Log.e("devide error!", String.valueOf(commitNum_devide_max));
        }

        teamgrass_block.addView(day_block);


        return teamgrass_block;*/
    }




}
