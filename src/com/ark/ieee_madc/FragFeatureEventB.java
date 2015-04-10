package com.ark.ieee_madc;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragFeatureEventB extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_feature_event_b, container, false);
		
		/*TextView tv_info = (TextView) view.findViewById(R.id.tv_pap);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "font/roboto_thin.ttf");
		tv_info.setTypeface(font);
		YoYo.with(Techniques.FadeInUp)
        .playOn(tv_info);*/
		
		return view;
	}
}