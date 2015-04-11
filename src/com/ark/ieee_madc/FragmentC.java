package com.ark.ieee_madc;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import me.relex.circleindicator.CircleIndicator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentC extends Fragment {

	LinearLayout bt_letsgo;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_c, container, false);
		
		bt_letsgo = (LinearLayout) view.findViewById(R.id.bt_fragc_letsgo);
		
		TextView tv_info = (TextView) view.findViewById(R.id.tv_letsgo);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "font/roboto_thin.ttf");
		tv_info.setTypeface(font);
		
		bt_letsgo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences sp = getActivity().getSharedPreferences("user_info", 0);
		    	Editor ed = sp.edit();
				ed.putInt("is_loged_in", 1);
		    	ed.commit();
				Intent i = new Intent(getActivity(),CatogeryActivity.class);
				startActivity(i);
				getActivity().finish();
			}
		});
		
        CircleIndicator defaultIndicator = (CircleIndicator) getActivity().findViewById(R.id.indicator_welcome);
        defaultIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int i, float v, int i2) {
            	
            	
            	
            	TextView tv_info = (TextView) getActivity().findViewById(R.id.tv_wel_info);
        		TextView tv_info1 = (TextView) getActivity().findViewById(R.id.tv_letsgo);
        		try {
					YoYo.with(Techniques.FadeInUp)
					.playOn(tv_info1);
					YoYo.with(Techniques.FadeInUp)
					.playOn(tv_info);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

            @Override public void onPageSelected(int i) {
                Log.d("OnPageChangeListener", "Current selected = " + i);
                ImageView iv1,iv2,iv3;//,iv4,iv5,iv6,iv7;
    			iv1 = (ImageView) getActivity().findViewById(R.id.iv_con);
    			iv2 = (ImageView) getActivity().findViewById(R.id.iv_spe);
    			iv3 = (ImageView) getActivity().findViewById(R.id.iv_pap);
                TextView tv_info = (TextView) getActivity().findViewById(R.id.tv_wel_info);
        		TextView tv_info1 = (TextView) getActivity().findViewById(R.id.tv_letsgo);
        		try {
					YoYo.with(Techniques.FadeInUp)
					.playOn(tv_info1);
					YoYo.with(Techniques.FadeInUp)
					.playOn(tv_info);
					YoYo.with(Techniques.BounceInUp)
					.playOn(iv1);
					YoYo.with(Techniques.BounceInDown)
					.playOn(iv2);
					YoYo.with(Techniques.BounceInDown)
					.playOn(iv3);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

            @Override public void onPageScrollStateChanged(int i) {

            }
        });
		
        
        
        
        
       
        
        
        
		return view;
	}
}





