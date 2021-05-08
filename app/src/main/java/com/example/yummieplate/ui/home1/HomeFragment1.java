package com.example.yummieplate.ui.home1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.yummieplate.MainActivity2;
import com.example.yummieplate.PlantsActivity;
import com.example.yummieplate.R;
import com.example.yummieplate.ViewPagerAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment1 extends Fragment {

    LinearLayout SliderDots;
    private int dotscount;
    private ImageView[] dots;
    ViewPager viewPager;

    CardView category1, category2, category3, category4, category5, category6;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home1, container, false);

        viewPager = root.findViewById(R.id.viewPager);
        SliderDots = root.findViewById(R.id.SliderDots);
        category1 = root.findViewById(R.id.category1);
        category2 = root.findViewById(R.id.category2);
        category3 = root.findViewById(R.id.category3);
        category4 = root.findViewById(R.id.category4);
        category5 = root.findViewById(R.id.category5);
        category6 = root.findViewById(R.id.category6);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];
        for(int i = 0; i < dotscount; i++){
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            SliderDots.addView(dots[i],params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i < dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new myTimeTask(), 2000,4000);

        category1.setOnClickListener(view -> startActivity(new Intent(getContext(), MainActivity2.class).putExtra("category", 1)));

        category2.setOnClickListener(view -> startActivity(new Intent(getContext(), MainActivity2.class).putExtra("category", 2)));

        category3.setOnClickListener(view -> startActivity(new Intent(getContext(), MainActivity2.class).putExtra("category", 3)));

        category4.setOnClickListener(view -> startActivity(new Intent(getContext(), MainActivity2.class).putExtra("category", 4)));

        category5.setOnClickListener(view -> {
            //startActivity(new Intent(getContext(), LiveTrackingFeatueActivity.class));
            startActivity(new Intent(getContext(), MainActivity2.class).putExtra("category", 5));
        });
        category6.setOnClickListener(view -> startActivity(new Intent(getContext(), PlantsActivity.class)));

        return root;
    }

    public class myTimeTask extends TimerTask{
        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2) {
                        viewPager.setCurrentItem(3);
                    } else if (viewPager.getCurrentItem() == 3) {
                        viewPager.setCurrentItem(4);
                    } else if (viewPager.getCurrentItem() == 4) {
                        viewPager.setCurrentItem(5);
                    } else if (viewPager.getCurrentItem() == 5) {
                        viewPager.setCurrentItem(6);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                });
            }
        }
    }

}