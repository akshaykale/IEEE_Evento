package com.ark.ieee_madc;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentB extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_b, container, false);

		TextView tv_info = (TextView) view.findViewById(R.id.tv_wel_info);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "font/roboto_thin.ttf");
		tv_info.setTypeface(font);
		try {
			YoYo.with(Techniques.FadeInUp)
			.playOn(tv_info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* ViewPager parallaxViewPager = (ViewPager) getActivity().findViewById(R.id.parallaxviewpager);
	        parallaxViewPager.setPageTransformer(false, new PageTransformer() {
	    		
	    		@Override
	    		public void transformPage(View view1, float position) {
	    			int pageWidth = view1.getWidth();
	    			
	    			ImageView iv1,iv2,iv3;//,iv4,iv5,iv6,iv7;
	    			iv1 = (ImageView) getActivity().findViewById(R.id.iv_con);
	    			iv2 = (ImageView) getActivity().findViewById(R.id.iv_spe);
	    			iv3 = (ImageView) getActivity().findViewById(R.id.iv_pap);
	    			iv4 = (ImageView) findViewById(R.id.i4);
	    			iv5 = (ImageView) findViewById(R.id.i5);
	    			iv6 = (ImageView) findViewById(R.id.i6);
	    			iv7 = (ImageView) findViewById(R.id.i7);
	     	       
	    			//ImageView img = (ImageView) findViewById(R.id.imgadobe);
	    	        //TextView tv1 = (TextView) findViewById(R.id.textView1);
	    		    if (position < -1) { // [-Infinity,-1)
	    		        // This page is way off-screen to the left.
	    		        view1.setAlpha(0);
	    		 
	    		    } else if (position <= 1) { // [-1,1]
	    		          
	    		    	//img.setTranslationX((float) (-(1 - position) * 0.5 * pageWidth));
	    				
	    				//iv2.setTranslationX((float) (-(1 - position) * pageWidth));
	    				 
	    				//iv6.setTranslationX((position) * (pageWidth / 2));
	    		 		//iv7.setTranslationX(-(position) * (pageWidth / 2));
	    				iv1.setTranslationX((position) * (pageWidth / 2));
	    				iv2.setTranslationX((position) * (pageWidth ));
	    				iv3.setTranslationX((position) * (pageWidth / 4));
	    				float MIN_SCALE = 0.1f;
						float scaleFactor = MIN_SCALE
	    	                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
	    				iv1.setScaleX(scaleFactor);
	    				iv2.setScaleX(scaleFactor);
	    				iv3.setScaleX(scaleFactor);
	    				//iv5.setTranslationX(-(position) * (pageWidth / 4));
	    		 
	    						  
	    				  
	    		    } else { // (1,+Infinity]
	    		        view1.setAlpha(0);
	    		    }
	    		}
	           	});
		*/
		
		
		
		return view;
	}
}
