[dl_jar]: https://www.statsmix.com/download/java/statsmix.jar
[dl_dep]: https://www.statsmix.com/download/java/statsmix-lib-dependencies.tar.gz
[track_exmpl]: https://github.com/mcclaskc/statsmix_jar/blob/master/examples/Track.java

StatsMix
========
This java library offers easy access to the StatsMix API. See www.statsmix.com/developers/documentation for more information.
What is StatsMix?
-----------------
StatsMix makes it easy to track, chart, and share application and business metrics. Use StatsMix to:

* Log every time a particular event happens (such as a user creating a new blog post)
* View a real-time chart of these application events in StatsMix's web UI
* Share the charts with users inside and outside your organization
* Create and share custom dashboards that aggregate multiple metrics together
  * Example dashboard: http://www.statsmix.com/d/0e788d59208900e7e3bc
  * Example embedded dashboard: http://www.statsmix.com/example-embedded 

To get started, you'll need an API key for StatsMix. You can get a free developer account here: http://www.statsmix.com/try?plan=developer

Full gem documentation is at http://www.statsmix.com/developers/java_library

Download and Install
--------------------
Include these jars in your build path: <br />
The jar file: [statsmix.jar] [dl_jar] <br />
It's dependencies: [statsmix-lib-dependencies.tar.gz] [dl_dep] <br /> 
Links to all the depedencies if you need to resolve version errors:
<ul>
	<li><a href="http://commons.apache.org/beanutils/">commons-beanutils</a></li>
	<li><a href="http://commons.apache.org/collections/">commons-collections</a></li>
	<li><a href="http://commons.apache.org/lang/">commons-lang</a></li>
	<li><a href="http://commons.apache.org/logging/">commons-logging</a></li>
	<li><a href="http://hc.apache.org/httpcomponents-client-ga/">httpcomponents-client</a></li>
	<li><a href="http://ezmorph.sourceforge.net/">ezmorph</a></li>
	<li><a href="http://json-lib.sourceforge.net/">json-lib</a></li>
</ul> 

Usage 
------
###Initialize
Create a new StatsMix Client
```java
import com.statsmix.*
Client smClient = new Client("YOUR_API_KEY");
```
###Track
The track method sends a request to the track API, and returns the XML response as a string. The following snippets were taken from [this example file.] [track_exmpl] 

Basic Tracking.  Adds a new stat with default value of 1 to the metric.
```java
smClient.track("Metric Name");
```

Track with a value other than one
```java
smClient.track("Metric Name", 5.2);
```

Track with additional properties
```java
List<NameValuePair> properties = new ArrayList<NameValuePair>(2);
properties.add(new BasicNameValuePair("value", "5.2"));  //if you do not include the value, it will default to 1
properties.add(new BasicNameValuePair("ref_id", "java01"));
properties.add(new BasicNameValuePair("generated_at", getDateTime()));
smClient.track("Metric Name", properties);
```

Track with meta data
```java
JSONObject meta = new JSONObject();
meta.put("food", "icecream");
meta.put("calories", 500);
smClient.track("Metric Name", properties, meta);
```

A method for formating the date
```java
public String getDateTime() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    return dateFormat.format(date);
}
```