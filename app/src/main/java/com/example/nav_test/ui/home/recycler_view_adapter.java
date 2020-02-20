package com.example.nav_test.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nav_test.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class recycler_view_adapter extends RecyclerView.Adapter<recycler_view_adapter.ViewHolder> {

    private ArrayList<Integer> mData = null;//commit 횟수 받아오기

    int max;
    String date;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView day;

        ViewHolder(View itemView){
            super(itemView);

            day = itemView.findViewById(R.id.days);//수정 필요!
        }

    }


        recycler_view_adapter(ArrayList<Integer> list,int max){
        mData = list;
        this.max = max;

    }

    @Override
    public recycler_view_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.grassblock_recyclerview_item,parent,false);
        recycler_view_adapter.ViewHolder vh = new recycler_view_adapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull recycler_view_adapter.ViewHolder holder, int position) {
        DateFormat only_d = new SimpleDateFormat("dd");


        Integer day_commit = mData.get(position);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE,(8-cal.get(Calendar.DAY_OF_WEEK)-7*52));
        cal.add(Calendar.DATE,position);



        holder.day.setText(only_d.format(cal.getTime()));


        float day_commit_divide_max = (float)day_commit/(float)max;
        if(day_commit_divide_max == 0){
            holder.day.setBackgroundColor(Color.parseColor("#ebedf0"));
            //Log.e("color","0");

        }
        else if(day_commit_divide_max<0.25){
            holder.day.setBackgroundColor(Color.parseColor("#c6e48b"));
            //Log.e("color","0.25");
        }
        else if(day_commit_divide_max<0.5){
            holder.day.setBackgroundColor(Color.parseColor("#7bc96f"));

        }
        else if(day_commit_divide_max<0.75){
            holder.day.setBackgroundColor(Color.parseColor("#239a3b"));

        }
        else if(day_commit_divide_max<=1){
            holder.day.setBackgroundColor(Color.parseColor("#196127"));
        }
        else{
            Log.e("divide error!", String.valueOf(day_commit_divide_max));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
