package com.statsmix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import net.sf.json.JSONObject;

public class Client {
	public String ApiKey;
	private static final String BASE_URL = "http://api.statsmix.com/api/v2/";

	public Client(String apiKey){
		ApiKey = apiKey;
	}

	public String track(String metricName){
		return track(metricName, new ArrayList<NameValuePair>(2));
	}
	
	public String track(String metricName, double value){
		List<NameValuePair> properties = new ArrayList<NameValuePair>(2);
		properties.add(new BasicNameValuePair("value", Double.toString(value)));
		return track(metricName, properties);
	}
	
	public String track(String metricName, List<NameValuePair> properties, JSONObject meta)
	{
    	properties.add(new BasicNameValuePair("meta", meta.toString()));
		return track(metricName, properties);
	}
	
	public String track(String metricName, List<NameValuePair> properties){
		properties.add(new BasicNameValuePair("name", metricName));
		return request("track", "post",  properties);
	}
	
	//stats functionality is undocumented and in progress. The downloadable jar will be updated when it is finished. 
	public String listStats(int metricId)
	{
		return listStats(metricId, new ArrayList<NameValuePair>(2));
	}
	public String listStats(int metricId, List<NameValuePair> options)
	{
		options.add(new BasicNameValuePair("metric_id", Integer.toString(metricId)));
		return request("stats", "get", options);
	}
	
	private String request(String resource, String method, List<NameValuePair> parameters){
		
	    HttpClient httpclient = new DefaultHttpClient();
	    
	    HttpUriRequest req = null;
	    parameters.add(new BasicNameValuePair("api_key", ApiKey));
		String paramString = URLEncodedUtils.format(parameters, "utf-8");
		String url = BASE_URL + resource + "?" + paramString;
	    if (method == "post") 
		{
			req = new HttpPost(url);
		}
		else if (method == "get") 
		{
			req = new HttpGet(url);
		}
	    
	    HttpResponse response = null;
	    String output = "";
	     try { 
	         response = httpclient.execute(req);
	         BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	         String line = "";
	         while ((line = rd.readLine()) != null) {
	           output += line;
	         }
	     } 
	     catch (IOException e) {} 
	     return output;
	}	
}
