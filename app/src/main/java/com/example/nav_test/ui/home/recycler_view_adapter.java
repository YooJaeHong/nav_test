package com.example.nav_test.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nav_test.R;

import java.util.ArrayList;

public class recycler_view_adapter extends RecyclerView.Adapter<recycler_view_adapter.ViewHolder> {

    private ArrayList<String> mData = null;//색깔 받아오기

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView day;

        ViewHolder(View itemView){
            super(itemView);

            day = itemView.findViewById(R.id.text1)//수정 필요!
        }

    }

    recycler_view_adapter(ArrayList<String> list){
        mData = list;
    }

    @Override
    public recycler_view_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_item,parent,false);
        recycler_view_adapter.ViewHolder vh = new recycler_view_adapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull recycler_view_adapter.ViewHolder holder, int position) {
        String day_color = mData.get(position);
        holder.day.setBackgroundColor(Color.parseColor(day_color));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
