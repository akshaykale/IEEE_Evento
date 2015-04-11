package com.ark.ieee_madc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ext.SatelliteMenu;
import android.view.ext.SatelliteMenu.SateliteClickedListener;
import android.view.ext.SatelliteMenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends Activity{

	TextView tv_name,tv_date,tv_location,tv_des,tv_org_name,
		tv_org_ph,tv_org_email,tv_url;
	LinearLayout ll_map,ll_cal;
	
	String event_name;
	String event_startdate;
	String event_enddate;
	String event_location;
	String event_description;
	String event_url;
	String event_contact;
	String event_organiser;
	String event_emailid;
	String event_lat;
	String event_long;
	String event_cat;
	
	private String srcLat = "20.856938";
	private String srcLong = "77.749857";
	private String destLat = "";
	private String destLong = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.details);
		
		init();
		
		 getValues();
		 
		 setValues();
		 
		 //Animate
		 Animate();
		 
		 //FloatingActionMenu
		 //FloatingMenu();
		 
		 //float d = CalcDist();
		 
		 ll_map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
						Uri.parse("http://maps.google.com/maps?saddr="+ srcLat +","+ srcLong +"&daddr="+ event_lat +","+ event_long +""));
						startActivity(intent);*/
				Uri geoLocation = Uri.parse("geo:0,0?q="+event_location.replace(" ", "+"));
				Intent intent = new Intent(Intent.ACTION_VIEW);
			    intent.setData(geoLocation);
			    if (intent.resolveActivity(getPackageManager()) != null) {
			        startActivity(intent);
			    }
			}
		});
		 ll_cal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar cal_start = Calendar.getInstance();  
				String[] dd = event_startdate.split("/");
				cal_start.set(Integer.parseInt(dd[2]),
						Integer.parseInt(dd[1]),
						Integer.parseInt(dd[0]));
				Calendar cal_end = Calendar.getInstance();  
				String[] ddd = event_enddate.split("/");
				cal_end.set(Integer.parseInt(ddd[2]),
						Integer.parseInt(ddd[1]),
						Integer.parseInt(ddd[0]));
				Intent intent = new Intent(Intent.ACTION_EDIT);
				intent.setType("vnd.android.cursor.item/event");
				intent.putExtra("beginTime", cal_start.getTimeInMillis());
				intent.putExtra("allDay", true);
				intent.putExtra("rrule", "FREQ=YEARLY");
				intent.putExtra("endTime", cal_end.getTimeInMillis());
				intent.putExtra("title", ""+event_name);
				intent.putExtra("eventLocation", event_location);
				startActivity(intent);
			}
		});
	}

	

	private void Animate() {
		try {
			YoYo.with(Techniques.FadeInUp)
			.playOn(tv_des);
			YoYo.with(Techniques.FadeInUp)
			.playOn(tv_org_email);
			YoYo.with(Techniques.FadeInUp)
			.playOn(tv_org_name);
			YoYo.with(Techniques.FadeInUp)
			.playOn(tv_org_ph);
			YoYo.with(Techniques.FadeInUp)
			.playOn(tv_url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String CalcDist() {
		Location loc1 = new Location("");
		loc1.setLatitude(Double.parseDouble(srcLat));
		loc1.setLongitude(Double.parseDouble(srcLong));

		Location loc2 = new Location("");
		loc2.setLatitude(Double.parseDouble(event_lat));
		loc2.setLongitude(Double.parseDouble(event_long));

		int distanceInMeters = (int)loc1.distanceTo(loc2)/1000;
		return ""+distanceInMeters;
	}
	
	private void setValues() {
		tv_name.setText(event_name);
		tv_date.setText(event_startdate+"\n"+event_enddate);
		tv_location.setText(event_location);
		tv_des.setText(event_description);
		tv_org_name.setText(event_organiser);
		tv_org_email.setText(event_emailid);
		tv_org_ph.setText(event_contact);
		tv_url.setText(event_url);
		
	}

	private void getValues() {
		event_name = getIntent().getStringExtra("name");
		 event_startdate = getIntent().getStringExtra("sdate");
		 event_enddate = getIntent().getStringExtra("edate");
		 event_location = getIntent().getStringExtra("loc");
		 event_description = getIntent().getStringExtra("des");
		 event_url = getIntent().getStringExtra("url");
		 event_contact = getIntent().getStringExtra("ph");
		 event_organiser = getIntent().getStringExtra("org_name");
		 event_emailid = getIntent().getStringExtra("email");
		 event_lat = getIntent().getStringExtra("lat");
		 event_long = getIntent().getStringExtra("long");
		 event_cat = getIntent().getStringExtra("cat");
		 Toast.makeText(getApplicationContext(), event_cat, 1).show();
		 ImageView iv = (ImageView) findViewById(R.id.ic_ico_dett);
		 //TextView tv = (TextView) findViewById(R.id.tv_cat_det);
		 
		 if(event_cat.equals(CatogeryActivity.KEY_CONFERENCE)){
			 
		 }else if(event_cat.equals(CatogeryActivity.KEY_LOCAL_AND_CAHPTER)){
			 iv.setImageResource(R.drawable.localevents_white);
			
		 }else if(event_cat.equals(CatogeryActivity.KEY_SPECTRUM)){
			 iv.setImageResource(R.drawable.spectrum_white);
			
		 }
		 
	}
	
/*	private void FloatingMenu() {
		SatelliteMenu menu = (SatelliteMenu) findViewById(R.id.menu);
		float distance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 170, getResources().getDisplayMetrics());
        menu.setSatelliteDistance((int) distance);
        menu.setExpandDuration(500);
        menu.setCloseItemsOnClick(false);
        menu.setTotalSpacingDegree(90);
        
        
        List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        //items.add(new SatelliteMenuItem(4, R.drawable.ic_action_time));
        items.add(new SatelliteMenuItem(5, R.drawable.ic_action_time));//exit
        items.add(new SatelliteMenuItem(4, R.drawable.ic_action_time));//notintrested
        items.add(new SatelliteMenuItem(3, R.drawable.ic_action_time));//addtocal
        items.add(new SatelliteMenuItem(2, R.drawable.ic_action_time));//getdir
        items.add(new SatelliteMenuItem(1, R.drawable.ic_action_time));//like
//        items.add(new SatelliteMenuItem(5, R.drawable.sat_item));
        menu.addItems(items);        
        
        menu.setOnItemClickedListener(new SateliteClickedListener() {
			
			public void eventOccured(int id) {
				switch(id){
				case 1://like
					Toast.makeText(getApplicationContext(), "Liked this Event", 1).show();
					break;
				case 2://get dir
					Toast.makeText(getApplicationContext(), "Getting Directions", 1).show();
					break;
				case 3://add to cal
					Toast.makeText(getApplicationContext(), "Adding...", 0).show();
					break;
				case 4://like
					Toast.makeText(getApplicationContext(), "Disliked this Event", 1).show();
					break;
				case 5://exit
					Toast.makeText(getApplicationContext(), "Exite", 0).show();
					break;
						
				}
			}
		});
	}*/
	

	private void init() {
		tv_name = (TextView) findViewById(R.id.det_tv_name);
		tv_date = (TextView) findViewById(R.id.det_tv_date);
		tv_location = (TextView) findViewById(R.id.det_tv_location);
		tv_des = (TextView) findViewById(R.id.det_tv_desc);
		tv_org_name = (TextView) findViewById(R.id.det_tv_org_name);
		tv_org_ph = (TextView) findViewById(R.id.det_tv_org_ph);
		tv_org_email = (TextView) findViewById(R.id.det_tv_org_email);
		tv_url = (TextView) findViewById(R.id.det_tv_url);
		ll_map = (LinearLayout) findViewById(R.id.det_tv_map);
		ll_cal = (LinearLayout) findViewById(R.id.det_tv_cal);
		
		
		Typeface font = Typeface.createFromAsset(getAssets(), "font/roboto_thin.ttf");
		tv_name.setTypeface(font);
		YoYo.with(Techniques.FadeInUp)
        .playOn(tv_name);
	}
}
