package com.ark.ieee_madc;

import me.relex.circleindicator.CircleIndicator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CatogeryActivity extends ActionBarActivity{

	public static String KEY_CONFERENCE = "Conference";
	public static String KEY_CALL_FOR_PAPERS = "Call for Papers";
	public static String KEY_SPECTRUM = "Spectrum Event";
	public static String KEY_LOCAL_AND_CAHPTER = "Local And Chapter Event";
	
	LinearLayout iv_allEvents, iv_conf_cat,iv_paper_cat,iv_local_cat, iv_spec_cat;
	
	//drawer
	LinearLayout ll_home,ll_people,ll_settings,ll_help,
	ll_Share,ll_About,ll_Exit,ll_Locate;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mdrawerListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.main_cat_layout);
		
		init();
		SetDrawer();
		
		//featured events
		setViewPager();
		
		//image click liseners
		CatClickEvents();
		DrawerLickListener();
	}

	

	private void init() {
		iv_allEvents = (LinearLayout) findViewById(R.id.ll_cat_allevents);
		iv_conf_cat = (LinearLayout) findViewById(R.id.ll_cat_conferences);
		//iv_paper_cat = (LinearLayout) findViewById(R.id.ll_cat_paper);
		iv_local_cat = (LinearLayout) findViewById(R.id.ll_cat_local_chapter_events);
		iv_spec_cat = (LinearLayout) findViewById(R.id.ll_cat_spec);
		
		//drawer
				mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
				//drawer ids
				//iv_nav =(ImageView) findViewById(R.id.imageViesdgfw1);
				//ll_help = (LinearLayout) findViewById(R.id.ll_Help);
				ll_home = (LinearLayout) findViewById(R.id.ll_Home);
				ll_people = (LinearLayout) findViewById(R.id.ll_People);
				ll_settings = (LinearLayout) findViewById(R.id.ll_Settings);
				ll_Share = (LinearLayout) findViewById(R.id.ll_Share);
				//ll_Locate = (LinearLayout) findViewById(R.id.ll_Locate);
				ll_About = (LinearLayout) findViewById(R.id.ll_About);
				ll_Exit = (LinearLayout) findViewById(R.id.ll_Exit);
	}

	private void setViewPager() {
		FragmentManager fragmentManager = getSupportFragmentManager();
        ViewPager parallaxViewPager = (ViewPager) findViewById(R.id.vp_feature_events);
        CircleIndicator defaultIndicator = (CircleIndicator) findViewById(R.id.indicator_default);
        parallaxViewPager.setAdapter(new MyFeatureEventAdapter(fragmentManager));
        defaultIndicator.setViewPager(parallaxViewPager);
	}
	
    /////////////drawer
    @SuppressLint("NewApi") private void SetDrawer() {
    	/**/
    	/*iv_nav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerLayout.openDrawer(Gravity.LEFT);
			}
		});*/
    	
    	mdrawerListener = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.string.drawer_open,R.string.drawer_close){
			@Override
			public void onDrawerClosed(View drawerView) {
			}
			@Override
			public void onDrawerOpened(View drawerView) {
			}
		};
		mDrawerLayout.setDrawerListener(mdrawerListener);//add the listener
		//Closing and Opening the Drawer///////////////////////////////////////
		
		//getActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
	}
    
    private void DrawerLickListener(){
    	ll_home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawers();
			}
		});
    	ll_people.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawers();
				Intent intentPeople = new Intent(CatogeryActivity.this,CreateEvent.class);
				startActivity(intentPeople);
			}
		});
    	ll_settings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentPeople = new Intent(CatogeryActivity.this,SettingsActivity.class);
				startActivity(intentPeople);
				mDrawerLayout.closeDrawers();
			}
		});
    	/*ll_help.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentPeople = new Intent(CatogeryActivity.this,HelpActivity.class);
				startActivity(intentPeople);
				mDrawerLayout.closeDrawers();
			}
		});*/
    	///////
    	ll_Share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
	            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            shareIntent.setType("text/plain");
	            String applink = "";
				shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, download this app! "+applink );
	            startActivity(shareIntent);
			}
		});
    	
    	ll_About.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentPeople = new Intent(CatogeryActivity.this,AboutActivity.class);
				startActivity(intentPeople);
				mDrawerLayout.closeDrawers();
			}
		});
    	ll_Exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
       	super.onPostCreate(savedInstanceState);
       	mdrawerListener.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	// TODO Auto-generated method stub
    	super.onConfigurationChanged(newConfig);
    	mdrawerListener.onConfigurationChanged(newConfig);
    }
    
	
	
	//key all -> allevents
	//key Technical -> technical
	//key Concert -> concert
	//key Cultural -> cultural
	//key Sports -> sports
	
	//key all -> allevents
		//key Conference -> technical
		//key Local And Chapter Event -> concert
		//key Call of Papers -> cultural
		//key Spectrum Events -> sports
	private void CatClickEvents() {
		iv_allEvents.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivityIntent("all");
			}
		});
		iv_conf_cat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivityIntent(KEY_CONFERENCE);
			}
		});
		iv_local_cat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivityIntent(KEY_LOCAL_AND_CAHPTER);
			}
		});
		/*iv_paper_cat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivityIntent(KEY_CALL_FOR_PAPERS);
			}
		});*/
		iv_spec_cat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivityIntent(KEY_SPECTRUM);
			}
		});
	}
	
	private void MainActivityIntent(String x) {
		Intent intent = new Intent(CatogeryActivity.this, MainActivity.class);
		intent.putExtra("which_cat", x);
		Toast.makeText(getApplicationContext(), x, 1).show();
		startActivity(intent);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    int id = item.getItemId();
	    if(mdrawerListener.onOptionsItemSelected(item)){
			return true;
		}
	    
	    return super.onOptionsItemSelected(item);
	}
}
	


///////////viewpager adapter

class MyFeatureEventAdapter extends FragmentPagerAdapter {

	public MyFeatureEventAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = null;

		if(arg0==0){
			fragment = new FragFeatureEventA();
		}if(arg0==1){
			fragment = new FragFeatureEventB();
		}if(arg0==2){
			fragment = new FragFeatureEventC();
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 3;
	}
}



