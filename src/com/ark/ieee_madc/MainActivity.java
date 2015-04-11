package com.ark.ieee_madc;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ark.ieee_madc.R.id;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidviewhover.BlurLayout;
import com.google.pb.CrossPB;
import com.melnykov.fab.FloatingActionButton;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import android.support.v7.app.ActionBarActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	ListView lv_list;
	//String[] strList;
	String intentCat = "all";
	ProgressBar pb;
	FloatingActionButton fab;
	ArrayList<Event> eventList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();

        intentCat = getIntent().getExtras().getString("which_cat");        
        
        //check connectivity
		if(isConnectingToInternet()){
			MDBRetrieveEvents_ r = new MDBRetrieveEvents_();
			r.execute("");
		}else{
			showNetworkDialog();
		}
		
		//set floating Action Button
		FloatingARViewButton();
		
    }


    


	private void init() {
    	eventList = new ArrayList<Event>();
    	lv_list = (ListView) findViewById(R.id.listView1);
    	
    	//set Progress bar
    	pb  = (ProgressBar) findViewById(id.google_progress);
    	pb.setIndeterminateDrawable(new CrossPB.Builder(this)
		.build());
    	pb.setVisibility(View.INVISIBLE);
	}
	
	private void FloatingARViewButton() {
    	fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.attachToListView(lv_list);
		fab.setVisibility(View.INVISIBLE);
		fab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "ARView", 1).show();
				startActivity(new Intent(MainActivity.this,TutorialLocationBasedAR.class));
			}
		});	
	}
    




    
    
    
    
    
    
    
////////////////##############################///////////////////
////CHECK CONNECTIVITY
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().
     		   getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null){
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null) 
                  for (int i = 0; i < info.length; i++) 
                      if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                          return true;
                      }
          }
          return false;
    } 
////////////////##############################///////////////////
////SHOW poor network dialog
   public void showNetworkDialog(){
	   Dialog d = new Dialog(MainActivity.this);
	   d.setTitle("No Internet Connection");
	   d.setCancelable(true);
	   d.show();
   }

/////////////////////////////////////////////////////////
///ListView Listener
   private void ListViewListener() {
		lv_list.setOnItemClickListener(new OnItemClickListener() {
       	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			
				Event ev = eventList.get(position);
				
				Intent detailsIntent = new Intent(MainActivity.this,Details.class);
				detailsIntent.putExtra("name", ev.getEvent_name());
				detailsIntent.putExtra("sdate", ev.getEvent_startdate());
				detailsIntent.putExtra("edate", ev.getEvent_enddate());
				detailsIntent.putExtra("loc", ev.getEvent_location());
				detailsIntent.putExtra("des", ev.getEvent_description());
				detailsIntent.putExtra("ph", ev.getEvent_contact());
				detailsIntent.putExtra("org_name", ev.getEvent_organiser());
				detailsIntent.putExtra("email", ev.getEvent_emailid());
				detailsIntent.putExtra("lat", ev.getEvent_lat());
				detailsIntent.putExtra("long", ev.getEvent_long());
				detailsIntent.putExtra("cat", ev.getEvent_cat());
				detailsIntent.putExtra("url", ev.getEvent_url());
				startActivity(detailsIntent);				
			}
		});
	}
    
    
    
////////////////##############################///////////////////
//////////////CLASSES
    
	//key all -> allevents
	//key Technical -> technical
	//key Concert -> concert
	//key Cultural -> cultural
	//key Sports -> sports
    public class MDBRetrieveEvents_ extends AsyncTask<String, String, List<Event>>{

    	@Override
    	protected void onPreExecute() {
    		pb.setVisibility(View.VISIBLE);
    		super.onPreExecute();
    	}
    	
    	@Override
    	protected List<Event> doInBackground(String... params) {
    		
    		MongoClient client = null;
			DBCursor cursor = null;
			try {
				MongoClientURI uri  = new MongoClientURI("mongodb://ieeemadc:ieeemadc@ds030827.mongolab.com:30827/madc"); 
				client = new MongoClient(uri);
				DB db = client.getDB(uri.getDatabase());           
				DBCollection coll = db.getCollection("ieee_events");           
				
				if(intentCat.equals("all")){
					Log.d("@@@@@", "got  "+intentCat);
					cursor = coll.find();
				}else{
					Log.d("@@@@@", "got  "+intentCat);
					BasicDBObject whereQuery = new BasicDBObject();
					whereQuery.put("event_cat", ""+intentCat);
					cursor = coll.find(whereQuery);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
            List<Event> eList = new ArrayList<Event>();
            
            if(cursor!=null)
            while (cursor.hasNext()) {
            	Event tempEvent = new Event();
            	DBObject doc = cursor.next();
            	
            	tempEvent.setEvent_id(doc.get("_id").toString());
                tempEvent.setEvent_name(doc.get("event_name").toString());
                tempEvent.setEvent_description(doc.get("event_description").toString());
                tempEvent.setEvent_location(doc.get("event_location").toString());
                tempEvent.setEvent_contact(doc.get("event_contact").toString());
                tempEvent.setEvent_emailid(doc.get("event_emailid").toString());
                tempEvent.setEvent_enddate(doc.get("event_enddate").toString());
                tempEvent.setEvent_lat(doc.get("event_latitude").toString());
                tempEvent.setEvent_long(doc.get("event_longitude").toString());
                tempEvent.setEvent_organiser(doc.get("event_organiser").toString());
                tempEvent.setEvent_startdate(doc.get("event_startdate").toString());
                tempEvent.setEvent_url(doc.get("event_url").toString());
                tempEvent.setEvent_cat(doc.get("event_cat").toString());
                eList.add(tempEvent);
            }
            client.close(); 		
    		return eList;
    	}
    	
    	@Override
    	protected void onPostExecute(List<Event> result) {
    		eventList = (ArrayList<Event>) result;
    		lv_list.setAdapter(new MyHowerAdapter(MainActivity.this));
    		LogEvents();
    		pb.setVisibility(View.INVISIBLE);
    		fab.setVisibility(View.VISIBLE);
    		super.onPostExecute(result);
    		
    	}

    } 
    
    void LogEvents(){
    	Toast.makeText(getApplicationContext(),
    			"Size: "+eventList.size(), 1).show();//+"\n"+eventList.get(0).getEvent_name()
    }
    
	class MyHowerAdapter extends BaseAdapter {

		Context context;
		BlurLayout mSampleLayout;
		
		TextView tv_loc,tv_date,tv_eventName;
		
		public MyHowerAdapter(Context c) {
			this.context = c;
		}
		@Override
		public int getCount() {
			return eventList.size();
		}
		@Override
		public Object getItem(int position) {
			return eventList.get(position);
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			LayoutInflater inflater = (LayoutInflater)
					context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.customhower_list, parent,false);
			
			int[] src = new int[]{R.drawable.conf_a, R.drawable.conf_b, R.drawable.conf_c};
			ImageView sr = (ImageView) row.findViewById(R.id.source);
			Random rn = new Random();
			int answer = rn.nextInt(3);
			sr.setImageResource(src[answer]);
			
			tv_loc = (TextView) row.findViewById(R.id.cardsimple_tv_location);
			tv_date = (TextView) row.findViewById(R.id.cardsimple_tv_date);
			tv_eventName = (TextView) row.findViewById(R.id.tv_event_name_list);
			
			tv_loc.setText(eventList.get(position).getEvent_location());
			tv_eventName.setText(eventList.get(position).getEvent_name());
			String date = eventList.get(position).getEvent_startdate()+
					" --> "+ eventList.get(position).getEvent_enddate();
			tv_date.setText(date);
			
			BlurLayout.setGlobalDefaultDuration(450);
	        mSampleLayout = (BlurLayout)row.findViewById(R.id.blur_layout);
	        View hover = LayoutInflater.from(context).inflate(R.layout.hower_layer, null);
	        hover.findViewById(R.id.heart).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                YoYo.with(Techniques.Tada)
	                    .duration(550)
	                    .playOn(v);
	                Toast.makeText(getApplicationContext(), "Added to Favourites", 0).show();
	            }
	        });
	        hover.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	//Double lat = Double.parseDouble(eventList.get(position).getEvent_lat());
	            	//Double longi = Double.parseDouble(eventList.get(position).getEvent_long());
	            	
	                YoYo.with(Techniques.Swing)
	                        .duration(550)
	                        .playOn(v);
	                Toast.makeText(getApplicationContext(), "Getting Directions", 0).show();
	            }
	        });
	        hover.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                YoYo.with(Techniques.Tada)
	                    .duration(550)
	                    .playOn(v);
	                LaunchDetails(position);
	            }
	        });
	        mSampleLayout.setHoverView(hover);
	        mSampleLayout.setBlurDuration(550);
	        mSampleLayout.addChildAppearAnimator(hover, R.id.heart, Techniques.FlipInX, 550, 0);
	        mSampleLayout.addChildAppearAnimator(hover, R.id.share, Techniques.FlipInX, 550, 250);
	        mSampleLayout.addChildAppearAnimator(hover, R.id.more, Techniques.FlipInX, 550, 500);

	        mSampleLayout.addChildDisappearAnimator(hover, R.id.heart, Techniques.FlipOutX, 550, 500);
	        mSampleLayout.addChildDisappearAnimator(hover, R.id.share, Techniques.FlipOutX, 550, 250);
	        mSampleLayout.addChildDisappearAnimator(hover, R.id.more, Techniques.FlipOutX, 550, 0);

	         YoYo.with(Techniques.FadeInUp)
            .playOn(tv_eventName);
	        
	         
	         ///onclick Listener
	         LinearLayout lay = (LinearLayout) row.findViewById(R.id.ll_card_clickable);
	         lay.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					LaunchDetails(position);
				}

				
			});
	        return row;
		}
		
		private void LaunchDetails(int position) {
			Event ev = eventList.get(position);
			
			Intent detailsIntent = new Intent(MainActivity.this,Details.class);
			detailsIntent.putExtra("name", ev.getEvent_name());
			detailsIntent.putExtra("sdate", ev.getEvent_startdate());
			detailsIntent.putExtra("edate", ev.getEvent_enddate());
			detailsIntent.putExtra("loc", ev.getEvent_location());
			detailsIntent.putExtra("des", ev.getEvent_description());
			detailsIntent.putExtra("ph", ev.getEvent_contact());
			detailsIntent.putExtra("org_name", ev.getEvent_organiser());
			detailsIntent.putExtra("email", ev.getEvent_emailid());
			detailsIntent.putExtra("lat", ev.getEvent_lat());
			detailsIntent.putExtra("long", ev.getEvent_long());
			detailsIntent.putExtra("cat", ev.getEvent_cat());
			detailsIntent.putExtra("url", ev.getEvent_url());
			startActivity(detailsIntent);
		}
	}
}
