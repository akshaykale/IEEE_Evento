package com.ark.ieee_madc;




import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

public class FragmentA extends Fragment {

	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.fragment_a, container, false);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "font/roboto_thin.ttf");
		
		
		
		((TextView) view.findViewById(R.id.tv_welcome)).setTypeface(font);
		((TextView) view.findViewById(R.id.tv)).setTypeface(font);
		//((TextView) view.findViewById(R.id.tv_ver)).setTypeface(font);
		
		return view;
	}
}
