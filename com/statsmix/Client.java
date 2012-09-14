package com.statsmix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
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
	
	
	// === TRACK === -----------------------------------------------------------------------------------------------
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
	
	
	// === STATS === -----------------------------------------------------------------------------------------------
	// listStats ---------------------------------------------------------------------------------------------------
	public String listStats(int metricId)
	{
		return listStats(metricId, new ArrayList<NameValuePair>(2));
	}
	public String listStats(int metricId, List<NameValuePair> options)
	{
		options.add(new BasicNameValuePair("metric_id", Integer.toString(metricId)));
		return request("stats", "get", options);
	}
	// getStat -----------------------------------------------------------------------------------------------------
	public String getStat(int metricId, int statId)
	{
		List<NameValuePair> properties = new ArrayList<NameValuePair>(2);
		properties.add(new BasicNameValuePair("metric_id", Integer.toString(metricId)));
		String resource = "stats/" + Integer.toString(statId) + ".xml";
		return request(resource, "get", properties);
	}
	public String getStat(int metricId, String refId)
	{
		List<NameValuePair> properties = new ArrayList<NameValuePair>(2);
		properties.add(new BasicNameValuePair("metric_id", Integer.toString(metricId)));
		properties.add(new BasicNameValuePair("ref_id", refId));
		String resource = "stats/" + refId + ".xml";
		return request(resource, "get", properties);
	}
	// createStat --------------------------------------------------------------------------------------------------
	public String createStat(int metricId)
	{
		return createStat(metricId, new ArrayList<NameValuePair>(2));
	}
	public String createStat(int metricId, int value)
	{
		List<NameValuePair> properties = new ArrayList<NameValuePair>(2);
		properties.add(new BasicNameValuePair("value", Integer.toString(value)));
		return createStat(metricId, properties);
	}
	public String createStat(int metricId, List<NameValuePair> properties, JSONObject meta)
	{
    	properties.add(new BasicNameValuePair("meta", meta.toString()));
		return createStat(metricId, properties);
	}
	public String createStat(int metricId, List<NameValuePair> properties)
	{
		properties.add(new BasicNameValuePair("metric_id", Integer.toString(metricId)));
		return request("stats", "post", properties);
	}
	// updateStat -------------------------------------------------------------------------------------------------
	public String updateStat(int metricId, int statId, int value)
	{
		List<NameValuePair> properties = new ArrayList<NameValuePair>(2);
		properties.add(new BasicNameValuePair("metric_id", Integer.toString(metricId)));
		properties.add(new BasicNameValuePair("value", Integer.toString(value)));
		String resource = "stats/" + Integer.toString(statId);
		return request(resource, "put", properties);
	}
	public String updateStat(int metricId, String refId, int value)
	{
		List<NameValuePair> properties = new ArrayList<NameValuePair>(2);
		properties.add(new BasicNameValuePair("metric_id", Integer.toString(metricId)));
		properties.add(new BasicNameValuePair("ref_id", refId));
		properties.add(new BasicNameValuePair("value", Integer.toString(value)));
		String resource = "stats/" + refId;
		return request(resource, "put", properties);
	}
	// deleteStat -------------------------------------------------------------------------------------------------
	public String deleteStat(int metricId, int statId)
	{
		List<NameValuePair> properties = new ArrayList<NameValuePair>(2);
		properties.add(new BasicNameValuePair("metric_id", Integer.toString(metricId)));
		String resource = "stats/" + Integer.toString(statId);
		return request(resource, "delete", properties);
	}
	public String deleteStat(int metricId, String refId)
	{
		List<NameValuePair> properties = new ArrayList<NameValuePair>(2);
		properties.add(new BasicNameValuePair("metric_id", Integer.toString(metricId)));
		properties.add(new BasicNameValuePair("ref_id", refId));
		String resource = "stats/" + refId;
		return request(resource, "delete", properties);
	}
	
	// In Dev
	// === METRICS === ---------------------------------------------------------------------------------------------
	// getMetric ---------------------------------------------------------------------------------------------------
	//public String getMetric(){return "todo";}
	// createMetric ------------------------------------------------------------------------------------------------
	//public String createMetric(){return "todo";}
	// updateMetric ------------------------------------------------------------------------------------------------
	//public String updateMetric(){return "todo";}
	// deleteMetric ------------------------------------------------------------------------------------------------
	//public String deleteMetric(){return "todo";}
	
	
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
		else if (method == "put")
		{
			req = new HttpPut(url);
		}
		else if (method == "delete")
		{
			req = new HttpDelete(url);
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
