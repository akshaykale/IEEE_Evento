// Copyright 2007-2014 metaio GmbH. All rights reserved.
package com.ark.ieee_madc;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.Lock;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

import com.metaio.cloud.plugin.util.MetaioCloudUtils;
import com.metaio.sdk.ARELInterpreterAndroidJava;
import com.metaio.sdk.ARViewActivity;
import com.metaio.sdk.MetaioDebug;
import com.metaio.sdk.jni.AnnotatedGeometriesGroupCallback;
import com.metaio.sdk.jni.EGEOMETRY_FOCUS_STATE;
import com.metaio.sdk.jni.IAnnotatedGeometriesGroup;
import com.metaio.sdk.jni.IGeometry;
import com.metaio.sdk.jni.IMetaioSDKCallback;
import com.metaio.sdk.jni.IRadar;
import com.metaio.sdk.jni.ImageStruct;
import com.metaio.sdk.jni.LLACoordinate;
import com.metaio.sdk.jni.Rotation;
import com.metaio.sdk.jni.SensorValues;
import com.metaio.sdk.jni.Vector3d;
import com.metaio.tools.io.AssetsManager;

public class TutorialLocationBasedAR extends ARViewActivity
{
	private IAnnotatedGeometriesGroup mAnnotatedGeometriesGroup;

	private MyAnnotatedGeometriesGroupCallback mAnnotatedGeometriesGroupCallback;

	/**
	 * Geometries
	 */
	private IGeometry mLondonGeo;
	private IGeometry mMunichGeo;
	private IGeometry mRomeGeo;
	private IGeometry mTokyoGeo;
	private IGeometry mParisGeo;

	private IGeometry mPICTGeo;private IGeometry mMITGeo;private IGeometry mBVPGeo;
	private IGeometry mVITGeo;private IGeometry mSinhgadGeo;private IGeometry mSymboisysGeo;
	private IGeometry mCOEPGeo;private IGeometry mDYPATILGeo;
	
	
  
	
	private IGeometry tempLocGeo;
	
	private IRadar mRadar;

	public void getIntentData(){
		int size = getIntent().getExtras().getInt("size");
		String inlat = "lat";
		String inlong = "long";
		//get latitude
		double[] latArr = null,longArr = null;
		for (int i =0 ; i<size ; i++){
			String tlat = inlat+""+i;
			String lat = getIntent().getExtras().getString(tlat);
			latArr[i] = Double.parseDouble(lat); 
		}
		for (int i =0 ; i<size ; i++){
			String tlong = inlong+""+i;
			String longi = getIntent().getExtras().getString(tlong);
			longArr[i] = Double.parseDouble(longi); 
		}
		for(int i = 0;i<size;i++){
			LLACoordinate tempCor = new LLACoordinate(latArr[i], longArr[i], 0, 0);
			tempLocGeo = createPOIGeometry(tempCor);
			mAnnotatedGeometriesGroup.addGeometry(tempLocGeo, "Paris");

		}

	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Set GPS tracking configuration
		boolean result = metaioSDK.setTrackingConfiguration("GPS", false);
		MetaioDebug.log("Tracking data loaded: " + result);
	}

	@Override
	protected void onDestroy()
	{
		// Break circular reference of Java objects
		if (mAnnotatedGeometriesGroup != null)
		{
			mAnnotatedGeometriesGroup.registerCallback(null);
		}

		if (mAnnotatedGeometriesGroupCallback != null)
		{
			mAnnotatedGeometriesGroupCallback.delete();
			mAnnotatedGeometriesGroupCallback = null;
		}

		super.onDestroy();
	}

	@Override
	public void onDrawFrame()
	{
		if (metaioSDK != null && mSensors != null)
		{
			SensorValues sensorValues = mSensors.getSensorValues();

			float heading = 0.0f;
			if (sensorValues.hasAttitude())
			{
				float m[] = new float[9];
				sensorValues.getAttitude().getRotationMatrix(m);

				Vector3d v = new Vector3d(m[6], m[7], m[8]);
				v.normalize();

				heading = (float)(-Math.atan2(v.getY(), v.getX()) - Math.PI / 2.0);
			}
			
			IGeometry geos[] = new IGeometry[] {mPICTGeo, mVITGeo, mBVPGeo, mMITGeo,mCOEPGeo,mSinhgadGeo,mSymboisysGeo};
			Rotation rot = new Rotation((float)(Math.PI / 2.0), 0.0f, -heading);
			for (IGeometry geo : geos)
			{
				if (geo != null)
				{
					geo.setRotation(rot);
				}
			}
		}

		super.onDrawFrame();
	}

	public void onButtonClick(View v)
	{
		finish();
	}

	@Override
	protected int getGUILayout()
	{
		return R.layout.tutorial_location_based_ar;
	}

	@Override
	protected IMetaioSDKCallback getMetaioSDKCallbackHandler()
	{
		return null;
	}

	@Override
	protected void loadContents()
	{
		try {
			AssetsManager.extractAllAssets(this, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		mAnnotatedGeometriesGroup = metaioSDK.createAnnotatedGeometriesGroup();
		mAnnotatedGeometriesGroupCallback = new MyAnnotatedGeometriesGroupCallback();
		mAnnotatedGeometriesGroup.registerCallback(mAnnotatedGeometriesGroupCallback);

		// Clamp geometries' Z position to range [5000;200000] no matter how close or far they are
		// away.
		// This influences minimum and maximum scaling of the geometries (easier for development).
		metaioSDK.setLLAObjectRenderingLimits(5, 200);

		// Set render frustum accordingly
		metaioSDK.setRendererClippingPlaneLimits(10, 220000);

		// let's create LLA objects for known cities
		LLACoordinate munich = new LLACoordinate(48.142573, 11.550321, 0, 0);
		LLACoordinate london = new LLACoordinate(51.50661, -0.130463, 0, 0);
		LLACoordinate tokyo = new LLACoordinate(35.657464, 139.773865, 0, 0);
		LLACoordinate rome = new LLACoordinate(41.90177, 12.45987, 0, 0);
		LLACoordinate paris = new LLACoordinate(48.85658, 2.348671, 0, 0);
		
		//pict 18.457929 73.85103
		//mit  18.511766  73.819759
		//BVP
		LLACoordinate pict = new LLACoordinate(18.457929, 73.85103, 0, 0);
		LLACoordinate mit = new LLACoordinate(18.511766, 73.819759, 0, 0);
		LLACoordinate bvp = new LLACoordinate(18.520499, 73.856973, 0, 0);
		LLACoordinate coep = new LLACoordinate(18.457523, 73.852049, 0, 0);
		LLACoordinate vit = new LLACoordinate(18.464077,73.867619, 0, 0);
		LLACoordinate sinhgad = new LLACoordinate(18.45543,73.818251, 0, 0);
		LLACoordinate sym = new LLACoordinate(18.522377,73.829252, 0, 0);
		LLACoordinate dyp = new LLACoordinate(18.623232,73.802251, 0, 0);

		
		// Load some POIs. Each of them has the same shape at its geoposition. We pass a string
		// (const char*) to IAnnotatedGeometriesGroup::addGeometry so that we can use it as POI
		// title
		// in the callback, in order to create an annotation image with the title on it.
		mPICTGeo = createPOIGeometry(pict);
		mAnnotatedGeometriesGroup.addGeometry(mPICTGeo, "Credenz, PICT, Pune");

		mMITGeo = createPOIGeometry(mit);
		mAnnotatedGeometriesGroup.addGeometry(mMITGeo, "Innovationx, MIT, Pune");

		mBVPGeo = createPOIGeometry(bvp);
		mAnnotatedGeometriesGroup.addGeometry(mBVPGeo, "College of Engineering, Pune");

		mCOEPGeo = createPOIGeometry(coep);
		mAnnotatedGeometriesGroup.addGeometry(mCOEPGeo, "Bharti Vidhyapeeth, Pune");

		mVITGeo = createPOIGeometry(vit);
		mAnnotatedGeometriesGroup.addGeometry(mVITGeo, "VIT, Pune");
		
		mSinhgadGeo = createPOIGeometry(sinhgad);
		mAnnotatedGeometriesGroup.addGeometry(mSinhgadGeo, "Karandak, Singgad College of Engg., Pune");

		mSymboisysGeo = createPOIGeometry(sym);
		mAnnotatedGeometriesGroup.addGeometry(mSymboisysGeo, "Symboisis College, S.B. road, Pune");

		mDYPATILGeo = createPOIGeometry(dyp);
		mAnnotatedGeometriesGroup.addGeometry(mDYPATILGeo, "D.Y. Patil Medical College, Pune");
		
		File metaioManModel =
				AssetsManager.getAssetPathAsFile(getApplicationContext(),
						"TutorialLocationBasedAR/Assets/metaioman.md2");
		if (metaioManModel != null)
		{
			//mMunichGeo = metaioSDK.createGeometry(metaioManModel);
			if (mMunichGeo != null)
			{
				//mMunichGeo.setTranslationLLA(munich);
				//mMunichGeo.setLLALimitsEnabled(true);
				//mMunichGeo.setScale(500);
			}
			else
			{
				MetaioDebug.log(Log.ERROR, "Error loading geometry: " + metaioManModel);
			}
		}

		// create radar
		mRadar = metaioSDK.createRadar();
		mRadar.setBackgroundTexture(AssetsManager.getAssetPathAsFile(getApplicationContext(),
				"TutorialLocationBasedAR/Assets/radar.png"));
		mRadar.setObjectsDefaultTexture(AssetsManager.getAssetPathAsFile(getApplicationContext(),
				"TutorialLocationBasedAR/Assets/yellow.png"));
		mRadar.setRelativeToScreen(IGeometry.ANCHOR_TL);

		// add geometries to the radar
		mRadar.add(mPICTGeo);
		mRadar.add(mBVPGeo);
		mRadar.add(mCOEPGeo);
		mRadar.add(mMITGeo);
		mRadar.add(mSinhgadGeo);
		mRadar.add(mVITGeo);
		mRadar.add(mSymboisysGeo);
	}

	private IGeometry createPOIGeometry(LLACoordinate lla)
	{
		final File path =
				AssetsManager.getAssetPathAsFile(getApplicationContext(),
						"TutorialLocationBasedAR/Assets/ExamplePOI.obj");
		if (path != null)
		{
			IGeometry geo = metaioSDK.createGeometry(path);
			geo.setTranslationLLA(lla);
			geo.setLLALimitsEnabled(true);
			geo.setScale(100);
			return geo;
		}
		else
		{
			MetaioDebug.log(Log.ERROR, "Missing files for POI geometry");
			return null;
		}
	}

	@Override
	protected void onGeometryTouched(final IGeometry geometry)
	{
		MetaioDebug.log("Geometry selected: " + geometry);

		mSurfaceView.queueEvent(new Runnable()
		{

			@Override
			public void run()
			{
				mRadar.setObjectsDefaultTexture(AssetsManager.getAssetPathAsFile(getApplicationContext(),
						"TutorialLocationBasedAR/Assets/yellow.png"));
				mRadar.setObjectTexture(geometry, AssetsManager.getAssetPathAsFile(getApplicationContext(),
						"TutorialLocationBasedAR/Assets/red.png"));
				mAnnotatedGeometriesGroup.setSelectedGeometry(geometry);
			}
		});
	}

	final class MyAnnotatedGeometriesGroupCallback extends AnnotatedGeometriesGroupCallback
	{
		Bitmap mAnnotationBackground, mEmptyStarImage, mFullStarImage;
		int mAnnotationBackgroundIndex;
		ImageStruct texture;
		String[] textureHash = new String[1];
		TextPaint mPaint;
		Lock geometryLock;


		Bitmap inOutCachedBitmaps[] = new Bitmap[] {mAnnotationBackground, mEmptyStarImage, mFullStarImage};
		int inOutCachedAnnotationBackgroundIndex[] = new int[] {mAnnotationBackgroundIndex};

		public MyAnnotatedGeometriesGroupCallback()
		{
			mPaint = new TextPaint();
			mPaint.setFilterBitmap(true); // enable dithering
			mPaint.setAntiAlias(true); // enable anti-aliasing
		}

		@Override
		public IGeometry loadUpdatedAnnotation(IGeometry geometry, Object userData, IGeometry existingAnnotation)
		{
			if (userData == null)
			{
				return null;
			}

			if (existingAnnotation != null)
			{
				// We don't update the annotation if e.g. distance has changed
				return existingAnnotation;
			}

			String title = (String)userData; // as passed to addGeometry
			LLACoordinate location = geometry.getTranslationLLA();
			float distance = (float)MetaioCloudUtils.getDistanceBetweenTwoCoordinates(location, mSensors.getLocation());
			Bitmap thumbnail = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
			try
			{
				texture =
						ARELInterpreterAndroidJava.getAnnotationImageForPOI(title, title, distance, "5", thumbnail,
								null,
								metaioSDK.getRenderSize(), TutorialLocationBasedAR.this,
								mPaint, inOutCachedBitmaps, inOutCachedAnnotationBackgroundIndex, textureHash);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if (thumbnail != null)
					thumbnail.recycle();
				thumbnail = null;
			}

			mAnnotationBackground = inOutCachedBitmaps[0];
			mEmptyStarImage = inOutCachedBitmaps[1];
			mFullStarImage = inOutCachedBitmaps[2];
			mAnnotationBackgroundIndex = inOutCachedAnnotationBackgroundIndex[0];

			IGeometry resultGeometry = null;

			if (texture != null)
			{
				if (geometryLock != null)
				{
					geometryLock.lock();
				}

				try
				{
					// Use texture "hash" to ensure that SDK loads new texture if texture changed
					resultGeometry = metaioSDK.createGeometryFromImage(textureHash[0], texture, true, false);
				}
				finally
				{
					if (geometryLock != null)
					{
						geometryLock.unlock();
					}
				}
			}

			return resultGeometry;
		}

		@Override
		public void onFocusStateChanged(IGeometry geometry, Object userData, EGEOMETRY_FOCUS_STATE oldState,
				EGEOMETRY_FOCUS_STATE newState)
		{
			MetaioDebug.log("onFocusStateChanged for " + (String)userData + ", " + oldState + "->" + newState);
		}
	}
}
