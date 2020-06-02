package com.servicenow.githubapitest.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.github.wnameless.json.flattener.JsonFlattener;

public class TestUtil {
	public static JSONObject responsejson;
	public static String responseString;

	// Method to get the value of the key by specifying it's path

	public String getValueByJPath(JSONObject responsejson, String jpath) {
		Object obj = responsejson;
		for (String s : jpath.split("/"))
			if (!s.isEmpty())
				if (!(s.contains("[") || s.contains("]")))
					obj = ((JSONObject) obj).get(s);
				else if (s.contains("[") || s.contains("]"))
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0]))
							.get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
		return obj.toString();
	}

	// Method to get the values of the particular key by specifying it's name

	public List<String> extract(String responseString, String key) {

		List<String> values = new ArrayList<String>();

		try {
			JSONObject jsonObj = new JSONObject(responseString);
			Map<String, Object> flattenedJsonMap = JsonFlattener.flattenAsMap(jsonObj.toString());
			flattenedJsonMap.forEach((k, v) -> {
				if (k.contains(key)) {
					values.add(v.toString());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return values;

	}

	// Second Method to get the values of the particular key by specifying it's name
	public List<String> getValuesForGivenKey(String jsonArrayStr, String key) {
		JSONArray jsonArray = new JSONArray(jsonArrayStr);
		return IntStream.range(0, jsonArray.length())
				.mapToObj(index -> ((JSONObject) jsonArray.get(index)).optString(key)).collect(Collectors.toList());
	}
  
	//Vaidation method to check the sorting of list in descending order
	public  boolean isSortedDesc(List<String> listOfStrings) {
        
		if (listOfStrings.size() ==0 || listOfStrings.size() == 1) {
	        return true;
	    }
		Iterator<String> iter = listOfStrings.iterator();
		String current, previous = iter.next();
		while (iter.hasNext()) {
			current = iter.next();
			if (previous.compareTo(current) > 0) {
				return true;
			}
			previous = current;
		}
		return false;
	}

}
