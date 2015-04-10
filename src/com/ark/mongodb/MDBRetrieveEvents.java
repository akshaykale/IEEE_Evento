package com.ark.mongodb;

import java.util.ArrayList;

import com.ark.ieee_madc.Event;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import android.os.AsyncTask;

public class MDBRetrieveEvents extends AsyncTask<String, String, ArrayList<Event>>{

	@Override
	protected void onPreExecute() {
		
		super.onPreExecute();
	}
	
	@SuppressWarnings("null")
	@Override
	protected ArrayList<Event> doInBackground(String... params) {
		
		MongoClientURI uri  = new MongoClientURI("mongodb://ieeemadc:ieeemadc@ds030827.mongolab.com:30827/madc"); 
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB(uri.getDatabase());
        
        DBCollection coll = db.getCollection("ieee_events");
        
        DBCursor cursor = coll.find();
        
        ArrayList<Event> eventList = null;
        
        int i=1;
        while (cursor.hasNext()) {
        	Event tempEvent = new Event();
        	DBObject doc = cursor.next();
        	tempEvent.setEvent_id(doc.get("_id").toString());
            tempEvent.setEvent_name(doc.get("event_name").toString());
            tempEvent.setEvent_name(doc.get("event_description").toString());
            tempEvent.setEvent_location(doc.get("event_location").toString());
            
            eventList.add(tempEvent);
            
            i++;
        }
        
        client.close();
		
		return eventList;
	}
	
	@Override
	protected void onPostExecute(ArrayList<Event> result) {
		
		super.onPostExecute(result);
	}

}
