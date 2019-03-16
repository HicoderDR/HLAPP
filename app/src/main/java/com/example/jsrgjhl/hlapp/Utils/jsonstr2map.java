package  com.example.jsrgjhl.hlapp.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class jsonstr2map {

public static Map<String, Object> jsonstr2map(String jsonStr) throws Exception {
Map<String, Object> map = new HashMap<String, Object>();
if (!jsonStr.isEmpty()) {
JSONObject json = new JSONObject(jsonStr);
Iterator i = json.keys();
while (i.hasNext()) {
String key = (String) i.next();
String value = json.getString(key);
if (value.indexOf("{") == 0) {
map.put(key.trim(), jsonstr2map(value));
} else if (value.indexOf("[") == 0) {
map.put(key.trim(), jsonstr2list(value));
} else {
map.put(key.trim(), value.trim());
}
}
}
return map;
}

/**
  * 以"["开头的String转list,list里装有map
  */
public static List<Map<String, Object>> jsonstr2list(String jsonStr) throws Exception {
List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
JSONArray ja = new JSONArray(jsonStr);
for (int j = 0; j < ja.length(); j++) {
String jm = ja.getString(j);
if (jm.indexOf("{") == 0) {
Map<String, Object> m = jsonstr2map(jm);
list.add(m);
}
}
return list;
}



}
