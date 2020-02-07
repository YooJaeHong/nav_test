package com.example.nav_test.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.nav_test.R;

import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class page_mygrass extends Fragment {

    int col_num = 6;
    LinkedList<String> all_date = new LinkedList<>();
    LinkedList<String> all_colors = new LinkedList<>();

    private Context mContext = null;
    private String github_id = "YooJaehong";
    private String htmlPageUrl = "https://github.com/" + github_id;

    private TextView textviewHtmlDocument;

    private String htmlContentInStringFormat;
    //TextView top_date = (TextView)findViewById(R.id.selected_date);//이 코드 실행하면 터짐(?)

    SimpleDateFormat git_hub_time_formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월");
    SimpleDateFormat day_only = new SimpleDateFormat("dd");







    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public page_mygrass() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        FrameLayout[] tv = new FrameLayout[25];




        String path = requireContext().getFilesDir().getPath();
        Log.e("receive path",path);
        File file = new File(path+"/myGrassData.txt");

        long start = System.currentTimeMillis();


        try {
            FileReader fileReader = new FileReader(file);
            //Log.e("save","1");
            BufferedReader bufferedReader =new BufferedReader(fileReader);
            //Log.e("save","2");
            String line= "";
            //Log.e("save","3");
            while((line = bufferedReader.readLine())!=null) {
                //Log.e("readed file", line);
                all_date.add(line);
                all_colors.add(bufferedReader.readLine());
            }
            //Log.e("save","4");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




        long end = System.currentTimeMillis();
        Log.d("timecheck","oncreate"+(end-start));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.page_mygrass, container, false);
        Log.e("page_mygrass","created");


        final TextView top_date = (TextView)root.findViewById(R.id.selected_date);

        //상단 스크롤바 버튼
        //col 주소를 tv에 저장
        final FrameLayout[] tv = new FrameLayout[25];
        for(int i=1;i<=6;i++){
            int col_num = i*4-2;

            try {
                int id = R.id.class.getField("col"+col_num+"row1_background_Frame").getInt(0);
                tv[i] = (FrameLayout)root.findViewById(id);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

        }
        //버튼조작

        Button left_btn = (Button)root.findViewById(R.id.left_btn);
        Button right_btn = (Button)root.findViewById(R.id.right_btn);

        DisplayMetrics display = this.getResources().getDisplayMetrics();

        int display_width = display.widthPixels;

        final HorizontalScrollView horizontalScrollView= (HorizontalScrollView)root.findViewById(R.id.내잔디밭_scrollView);
        horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        });
        final int halfScreenWidth = (int)(display_width*0.5f);
        Log.e("halfscreen = ",Integer.toString(halfScreenWidth));
        Log.e("tv[col_num].getLeft",Integer.toString(tv[col_num].getLeft()));
        left_btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                if(col_num>1){
                    col_num--;
                    horizontalScrollView.smoothScrollTo(tv[col_num].getLeft() - halfScreenWidth,0);//스크롤이동


                    //날짜변환
                    try {
                        Date original_date = formatter.parse(top_date.getText().toString());
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(original_date);
                        cal.add(Calendar.MONTH,-1);


                        String scroll_starter_date = formatter.format(cal.getTime());
                        top_date.setText(scroll_starter_date);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }



            }
        });
        right_btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                if(col_num<6){
                    col_num++;
                    horizontalScrollView.smoothScrollTo(tv[col_num].getLeft() - halfScreenWidth,0);//스크롤이동
                    try {
                        Date original_date = formatter.parse(top_date.getText().toString());
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(original_date);
                        cal.add(Calendar.MONTH,1);


                        String scroll_starter_date = formatter.format(cal.getTime());
                        top_date.setText(scroll_starter_date);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Log.e("try 이전:","abce");
        //Document doc = null;
        Elements dates = null;


        //loading Activity 에서 입력한 목록 입력






        //파싱한 데이터로 초기 날짜 설정
        Calendar cal = Calendar.getInstance();
        Date original_date = null;

        try {

            Log.e("num of date : ",Integer.toString(all_date.size()));
            Log.e("num of color : ",Integer.toString(all_colors.size()));

            if(all_date.size()!=0) {
                original_date = git_hub_time_formatter.parse(all_date.get(all_date.size()-1));
            }
            //요일계산용 마지막 날짜 저장

            cal.setTime(original_date);


            String scroll_starter_date = formatter.format(original_date);
            top_date.setText(scroll_starter_date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }


        int week = cal.get(Calendar.DAY_OF_WEEK);
        //파싱한 데이터를 기반으로 색상변경

        cal.add(Calendar.DATE,7-week);
        long push_block_color_start = System.currentTimeMillis();
        for(int j=1;j<=7-week;j++){
            try {

                String day = day_only.format(cal.getTime());
                int colrowid = R.id.class.getField("col24row"+(8-j)).getInt(0);
                TextView colrow =(TextView)root.findViewById(colrowid);
                colrow.setText(day);
                cal.add(Calendar.DATE,-1);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        //이번주 아직 안온날들 제외
        boolean today = true;

        LinkedList<String> all_colors = (LinkedList<String>)(this.all_colors).clone();
        LinkedList<String> all_date = (LinkedList<String>)(this.all_date).clone();

        for(int j=8-week;j<=7;j++){
            String color_temp = all_colors.pollLast();



            try {
                Date selected_date = git_hub_time_formatter.parse(all_date.pollLast());
                String day = day_only.format(selected_date);
                int colrowid = R.id.class.getField("col24row"+(8-j)).getInt(0);

                TextView colrow = (TextView)root.findViewById(colrowid);
                colrow.setBackgroundColor(Color.parseColor(color_temp));
                colrow.setText(day);
                if(day.equals("01")){
                    colrow.setTextColor(Color.RED);
                }
                if(today ==true){
                    View border = (View)root.findViewById(colrowid).getParent();
                    border.setBackgroundColor(Color.BLUE);
                    today = false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }
        //나머지요일
        for(int i=2;i<=24;i++){
            for(int j=1;j<=7;j++){
                String color_temp = all_colors.pollLast();


                try {
                    Date selected_date = git_hub_time_formatter.parse(all_date.pollLast());
                    String day = day_only.format(selected_date);
                    int colrowid = R.id.class.getField("col"+(25-i)+"row"+(8-j)).getInt(0);
                    TextView colrow = (TextView)root.findViewById(colrowid);
                    colrow.setBackgroundColor(Color.parseColor(color_temp));
                    colrow.setText(day);
                    if(day.equals("01")){
                        colrow.setTextColor(Color.RED);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        long push_block_color_end = System.currentTimeMillis();

        Log.d("timecheck","time:" +(push_block_color_end-push_block_color_start));









        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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

}
