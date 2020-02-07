package com.example.nav_test.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.nav_test.R;
import com.google.android.material.tabs.TabLayout;

import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class mygrass extends Fragment {


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

    //tablayout
    private TabLayout mTabLayout;
    private ViewPager viewpager;
    private tabPagerAdapter pagerAdapter;

    //tabview 끝
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        //root 생성
        long push_block_start = System.currentTimeMillis();
        View root = inflater.inflate(R.layout.activity_mygrass, container, false);

        long push_block_end = System.currentTimeMillis();
        //tablayout
        mTabLayout = (TabLayout) root.findViewById(R.id.layout_tab);

        mTabLayout.addTab(mTabLayout.newTab().setText("내잔디밭"));
        mTabLayout.addTab(mTabLayout.newTab().setText("팀잔디밭"));
        mTabLayout.addTab(mTabLayout.newTab().setText("새로운잔디밭"));


        viewpager = (ViewPager) root.findViewById((R.id.pager_content));
        viewpager.setOffscreenPageLimit(mTabLayout.getTabCount());

        pagerAdapter = new tabPagerAdapter(getChildFragmentManager(),mTabLayout.getTabCount());

        viewpager.setAdapter(pagerAdapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        Log.e("push block time", Long.toString(push_block_end - push_block_start));

        return root;
    }
}
