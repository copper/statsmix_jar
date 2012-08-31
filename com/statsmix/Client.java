package com.statsmix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import net.sf.json.JSONObject;

public class Client {
	public String API_KEY;
	private static final String BASE_URL = "http://www.statsmix.com/api/v2/";

	public Client(String api_key){
		API_KEY = api_key;
	}
	
	public String track(String metric_name){
		return track(metric_name, new ArrayList<NameValuePair>(2));
	}
	
	public String track(String metric_name, double value){
		List<NameValuePair> properties = new ArrayList<NameValuePair>(2);
		properties.add(new BasicNameValuePair("value", Double.toString(value)));
		return track(metric_name, properties);
	}
	
	public String track(String metric_name, List<NameValuePair> properties, JSONObject meta)
	{
    	properties.add(new BasicNameValuePair("meta", meta.toString()));
		return track(metric_name, properties);
	}
	
	public String track(String metric_name, List<NameValuePair> properties){
		properties.add(new BasicNameValuePair("name", metric_name));
		return request("track", properties);
	}
	
	private String request(String resource, List<NameValuePair> parameters){
		
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(BASE_URL + resource);  
	    HttpResponse response = null;
	    String output = "";
	    parameters.add(new BasicNameValuePair("api_key", API_KEY));
	     try { 

	         httppost.setEntity(new UrlEncodedFormEntity(parameters));
	         response = httpclient.execute(httppost);
	         BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	         String line = "";
	         while ((line = rd.readLine()) != null) {
	           output += line;
	         }
	      } 
	     catch (ClientProtocolException e) {}
	     catch (IOException e) {} 
	     return output;
	}
}
