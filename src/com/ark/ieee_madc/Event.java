package com.ark.ieee_madc;

public class Event {
	

	String event_id;
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
	
	public Event() {
		 event_id = null;
		 event_name = null;
		 event_startdate = null;
		 event_enddate = null;
		 event_location = null;
		 event_description = null;
		 event_url = null;
		 event_contact = null;
		 event_organiser = null;
		 event_emailid = null;
		 event_lat = null;
		 event_long = null;
		 event_cat = null;
	}
	
	
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public String getEvent_name() {
		return event_name;
	}
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	public String getEvent_startdate() {
		return event_startdate;
	}
	public void setEvent_startdate(String event_startdate) {
		this.event_startdate = event_startdate;
	}
	public String getEvent_enddate() {
		return event_enddate;
	}
	public void setEvent_enddate(String event_enddate) {
		this.event_enddate = event_enddate;
	}
	public String getEvent_location() {
		return event_location;
	}
	public void setEvent_location(String event_location) {
		this.event_location = event_location;
	}
	public String getEvent_description() {
		return event_description;
	}
	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}
	public String getEvent_url() {
		return event_url;
	}
	public void setEvent_url(String event_url) {
		this.event_url = event_url;
	}
	public String getEvent_contact() {
		return event_contact;
	}
	public void setEvent_contact(String event_contact) {
		this.event_contact = event_contact;
	}
	public String getEvent_organiser() {
		return event_organiser;
	}
	public void setEvent_organiser(String event_organiser) {
		this.event_organiser = event_organiser;
	}
	public String getEvent_emailid() {
		return event_emailid;
	}
	public void setEvent_emailid(String event_emailid) {
		this.event_emailid = event_emailid;
	}


	public String getEvent_lat() {
		return event_lat;
	}


	public void setEvent_lat(String event_lat) {
		this.event_lat = event_lat;
	}


	public String getEvent_long() {
		return event_long;
	}


	public void setEvent_long(String event_long) {
		this.event_long = event_long;
	}


	public String getEvent_cat() {
		return event_cat;
	}


	public void setEvent_cat(String event_cat) {
		this.event_cat = event_cat;
	}
}
