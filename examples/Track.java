package examples;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.statsmix.Client;

public class Track {
	
	private static String API_KEY = "your_api_key";
	
	public static void main(String [] args){
		//Create a new client
		Client smClient = new Client(API_KEY);
		
		//Basic Tracking.  Adds a new stat with default value of 1 to the metric.
		smClient.track("Metric Name");
		
		//Track with a value other than one
		smClient.track("Metric Name", 5.2);
		
		//Track with additional properties
		List<NameValuePair> properties = new ArrayList<NameValuePair>(2);
		properties.add(new BasicNameValuePair("value", "5.2")); //Value is not required, it will default to one if not included
		properties.add(new BasicNameValuePair("ref_id", "java01"));
		properties.add(new BasicNameValuePair("generated_at", getDateTime()));
		smClient.track("Metric Name", properties);
		
		//Track with meta data
		JSONObject meta = new JSONObject();
		meta.put("food", "icecream");
		meta.put("calories", 500);
		smClient.track("Metric Name", properties, meta);
	}
	
	public static String getDateTime() {
	    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    Date date = new Date();
	    return dateFormat.format(date);
	}
}
