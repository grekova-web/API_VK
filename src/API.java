import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.*;

class API {
    public static void main(String[] args) throws IOException, JSONException {
        HashMap<String, Integer> cities = new HashMap<>();
        String api = "https://api.vk.com/method/groups.getMembers?" +
                "group_id=iritrtf_urfu&fields=city&access_token=" +
                "f5493d1ef5493d1ef5493d1e03f53ddc23ff549f5493d1eaae58a467485251565e27d2a&v=5.126";
        URL url = new URL(api);
        URLConnection connection = url.openConnection();
        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = readAll(rd);
        rd.close();

        JSONObject members = new JSONObject(line);
        JSONArray arrayOfMembers = members.getJSONObject("response").getJSONArray("items");

        for (int i=0; i < arrayOfMembers.length(); i++) {

            JSONObject info = arrayOfMembers.getJSONObject(i);
            String cityName;
            try {
                JSONObject city = info.getJSONObject("city");
                cityName = city.get("title").toString();
                cities.put(cityName, cities.containsKey(cityName) ? cities.get(cityName) + 1 : 1);
            } catch (Exception e) { }

        }
        // Выводит список состоящий из названия города и количества участников сообщества
        // "Абитуриент ИРИТ-РТФ" из этого города

        for(Map.Entry<String, Integer> item : cities.entrySet()) {
            System.out.println(item.getKey() +": " + item.getValue());
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
