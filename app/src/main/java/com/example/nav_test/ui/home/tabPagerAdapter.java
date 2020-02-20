package com.example.nav_test.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.nav_test.R;

public class tabPagerAdapter extends FragmentPagerAdapter {
    private Context mContext = null;

    private  int tabCount;

    Fragment[] tab_num= null;




    public void tabPagerAdapter(){}

    public tabPagerAdapter(FragmentManager fm, int tabcount){

        super(fm);
        this.tabCount =tabcount;


        tab_num = new Fragment[3];
        tab_num[0] = new page_mygrass();
        tab_num[1] = new page_teamgrass();
        tab_num[2] = new page_newgrass();


    }

    @Override
    public Fragment getItem(int position){
        Log.e("position",Integer.toString(position));
        /*switch(position){
            case 0:
                page_mygrass mygrass = new page_mygrass();
                Log.e("position","0");
                return mygrass;
            case 1:
                page_teamgrass teamgrass = new page_teamgrass();
                Log.e("position","");
                return teamgrass;
            case 2:
                page_newgrass newgrass = new page_newgrass();
                Log.e("position","2");
                return newgrass;
            case 3:
                page_mygrass mygrass4 = new page_mygrass();
                Log.e("position","3");
                return mygrass4;
            default:
                page_mygrass mygrassde = new page_mygrass();
                Log.e("position","de");
                return mygrassde;



        }*/

        return tab_num[position];
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }





    @Override
    public int getCount() {
        return tabCount;
    }

    /*@Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }*/
    /*@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 뷰페이저에서 삭제.
         (ViewPager) container.removeView((ViewGroup) object);
    }*/
}