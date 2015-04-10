package com.ark.ieee_madc;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import me.relex.circleindicator.CircleIndicator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Welcome extends ActionBarActivity {
	
	Button bt_go;
	
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
    	int ch = sp.getInt("is_loged_in", 0);
    	if(ch!=1){
    		
    	}else{
    		startActivity(new Intent(Welcome.this,CatogeryActivity.class));
    		finish();
    	}
    	
    	FragmentManager fragmentManager = getSupportFragmentManager();
        CircleIndicator defaultIndicator = (CircleIndicator) findViewById(R.id.indicator_welcome);
        ViewPager parallaxViewPager = (ViewPager) findViewById(R.id.parallaxviewpager);
        parallaxViewPager.setAdapter(new MyAdapter(fragmentManager));
        defaultIndicator.setViewPager(parallaxViewPager);
        
        defaultIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int i, float v, int i2) {
            	
            	TextView tv_info = (TextView) findViewById(R.id.tv_wel_info);
        		try {
					YoYo.with(Techniques.FadeInUp)
					.playOn(tv_info);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
            }

            @Override public void onPageSelected(int i) {
                Log.d("OnPageChangeListener", "Current selected = " + i);
                TextView tv_info = (TextView) findViewById(R.id.tv_wel_info);
        		try {
					YoYo.with(Techniques.FadeInUp)
					.playOn(tv_info);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
            }

            @Override public void onPageScrollStateChanged(int i) {

            }
        });
        
        parallaxViewPager.setPageTransformer(false, new PageTransformer() {
    		
    		@Override
    		public void transformPage(View view, float position) {
    			int pageWidth = view.getWidth();
    			
    			//ImageView iv1,iv2,iv3;//,iv4,iv5,iv6,iv7;
    			//iv1 = (ImageView) findViewById(R.id.iv_con);
    			//iv2 = (ImageView) findViewById(R.id.iv_spe);
    			//iv3 = (ImageView) findViewById(R.id.iv_pap);
    			//iv4 = (ImageView) findViewById(R.id.i4);
    			//iv5 = (ImageView) findViewById(R.id.i5);
    			//iv6 = (ImageView) findViewById(R.id.i6);
    			//iv7 = (ImageView) findViewById(R.id.i7);
     	       
    			//ImageView img = (ImageView) findViewById(R.id.imgadobe);
    	        //TextView tv1 = (TextView) findViewById(R.id.textView1);
    		    if (position < -1) { // [-Infinity,-1)
    		        // This page is way off-screen to the left.
    		        view.setAlpha(0);
    		 
    		    } else if (position <= 1) { // [-1,1]
    		          
    		    	//img.setTranslationX((float) (-(1 - position) * 0.5 * pageWidth));
    				
    				//iv2.setTranslationX((float) (-(1 - position) * pageWidth));
    				 
    				//iv6.setTranslationX((position) * (pageWidth / 2));
    		 		//iv7.setTranslationX(-(position) * (pageWidth / 2));
    				//iv1.setTranslationX((position) * (pageWidth / 2));
    				//iv2.setTranslationX(-(position) * (pageWidth / 2));
    				//iv3.setTranslationX((position) * (pageWidth / 4));
    				//iv5.setTranslationX(-(position) * (pageWidth / 4));
    		 
    						  
    				  
    		    } else { // (1,+Infinity]
    		        view.setAlpha(0);
    		    }
    		}
           	});
    }
}

class MyAdapter extends FragmentPagerAdapter {
	public MyAdapter(FragmentManager fm) {
		super(fm);
	}
	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = null;
		if(arg0==0){
			fragment = new FragmentA();
		}if(arg0==1){
			fragment = new FragmentB();
		}if(arg0==2){
			fragment = new FragmentC();
		}		
		return fragment;
	}
	@Override
	public int getCount() {
		return 3;
	}
	
}
