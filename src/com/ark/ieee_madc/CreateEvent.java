package com.ark.ieee_madc;

import com.ark.ieee_madc.R.id;
import com.google.pb.CrossPB;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

public class CreateEvent extends ActionBarActivity{

	
	EditText et_name,et_desc,et_url,et_loc,et_startdate,et_enddate,et_lat,et_long,et_org_name,et_org_ph,et_org_email;
	
	RadioButton rb_conf,rb_local,rb_spec;
	
	String event_name = null;
	String event_startdate = null;
	String event_enddate = null;
	String event_location = null;
	String event_description = null;
	String event_url = null;
	String event_contact = null;
	String event_organiser = null;
	String event_emailid = null;
	String event_lat = null;
	String event_long = null;
	String event_cat = "all";
	
	LinearLayout ll_create;
	
	ProgressBar pb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_event);
		
		init();
		
		ll_create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				event_name = et_name.getText().toString();
				event_description = et_desc.getText().toString();
				event_url = et_url.getText().toString();
				event_location = et_loc.getText().toString();
				event_startdate = et_startdate.getText().toString();
				event_enddate = et_enddate.getText().toString();
				event_lat = et_lat.getText().toString();
				event_long = et_long.getText().toString();
				event_organiser = et_org_name.getText().toString();
				event_emailid = et_org_email.getText().toString();
				event_contact = et_org_ph.getText().toString();
				
				if(rb_conf.isChecked()){
					event_cat = CatogeryActivity.KEY_CONFERENCE;
				}if(rb_local.isChecked()){
					event_cat = CatogeryActivity.KEY_LOCAL_AND_CAHPTER;
				}if(rb_spec.isChecked()){
					event_cat = CatogeryActivity.KEY_SPECTRUM;
				}
				
				MongoCreateEvent mc = new MongoCreateEvent();
				mc.execute("");
			}
		});
	}
	private void init() {
		et_name = (EditText) findViewById(R.id.et_eventname);
		et_desc = (EditText) findViewById(R.id.et_desc);
		et_url = (EditText) findViewById(R.id.et_event_weburl);
		et_loc = (EditText) findViewById(R.id.et_loc);
		et_startdate = (EditText) findViewById(R.id.et_event_start);
		et_enddate = (EditText) findViewById(R.id.et_end);
		et_lat = (EditText) findViewById(R.id.et_lat);
		et_long = (EditText) findViewById(R.id.et_long);
		et_org_name = (EditText) findViewById(R.id.et_org_name);
		et_org_ph = (EditText) findViewById(R.id.et_org_ph);
		et_org_email = (EditText) findViewById(R.id.et_org_email);
		
		ll_create = (LinearLayout) findViewById(R.id.bt_fragc_create);
		
		rb_conf = (RadioButton) findViewById(R.id.rb_conf);
		rb_local = (RadioButton) findViewById(R.id.rb_local);
		rb_spec = (RadioButton) findViewById(R.id.rb_spec);
		
		
		event_name = et_name.getText().toString();
		event_description = et_desc.getText().toString();
		event_url = et_url.getText().toString();
		event_location = et_loc.getText().toString();
		event_startdate = et_startdate.getText().toString();
		event_enddate = et_enddate.getText().toString();
		event_lat = et_lat.getText().toString();
		event_long = et_long.getText().toString();
		event_organiser = et_org_name.getText().toString();
		event_emailid = et_org_email.getText().toString();
		event_contact = et_org_ph.getText().toString();
		
		
		
		//set Progress bar
    	pb  = (ProgressBar) findViewById(id.google_progress_);
    	pb.setIndeterminateDrawable(new CrossPB.Builder(this)
		.build());
    	pb.setVisibility(View.INVISIBLE);
	}


class MongoCreateEvent extends AsyncTask<String, String, String>{

	@Override
	protected void onPreExecute() {
		pb.setVisibility(View.VISIBLE);
		ll_create.setVisibility(View.INVISIBLE);
		super.onPreExecute();
	}
	@Override
	protected String doInBackground(String... params) {
		
	      MongoClientURI uri  = new MongoClientURI("mongodb://ieeemadc:ieeemadc@ds030827.mongolab.com:30827/madc"); 
	        MongoClient client = new MongoClient(uri);
	        DB db = client.getDB(uri.getDatabase());
	        
	    	
	        
	        BasicDBObject newEvent = new BasicDBObject();
	        newEvent.put("event_name", event_name);
	        newEvent.put("event_description", event_description);
	        newEvent.put("event_location", event_location);
	        newEvent.put("event_contact", event_contact);
	        newEvent.put("event_emailid", event_emailid);
	        newEvent.put("event_enddate", event_enddate);
	        newEvent.put("event_latitude", event_lat);
	        newEvent.put("event_longitude", event_long);
	        newEvent.put("event_organiser", event_organiser);
	        newEvent.put("event_startdate", event_startdate);
	        newEvent.put("event_url", event_url);
	        newEvent.put("event_cat", event_cat);
	    	
	    	

	        
	        DBCollection songs = db.getCollection("ieee_events");
	        songs.insert(newEvent);
	        //songs.insert(newEvent2);
	        //songs.insert(newEvent3);
	        //songs.insert(newEvent4);
	        //songs.insert(newEvent5);
	        //songs.insert(newEvent6);
	        //songs.insert(newEvent7);
	        client.close();
		
		return "Success";
	}
	
	@Override
	protected void onPostExecute(String result) {
		if(result.equals("Success")){
			Toast.makeText(getApplicationContext(), "Successfully Added", 1).show();
		}
		pb.setVisibility(View.INVISIBLE);
		ll_create.setVisibility(View.VISIBLE);
		
		super.onPostExecute(result);
	}

}
}
