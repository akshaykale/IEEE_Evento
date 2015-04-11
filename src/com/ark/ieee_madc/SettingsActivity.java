package com.ark.ieee_madc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SettingsActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_lay);
		
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll_set_reset);
		ll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences sp = getSharedPreferences("user_info", 0);
		    	Editor ed = sp.edit();
				ed.putInt("is_loged_in", 0);
		    	ed.commit();
		    	
		    	Toast.makeText(getApplicationContext(), "App Reset Successfully\nRestart the App ", 1).show();
			}
		});
	}

}