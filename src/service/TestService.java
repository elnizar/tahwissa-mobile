/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.codename1.maps.Coord;
import entity.Evenement;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Meedoch
 */
public class TestService {

    private static String googleDistanceMatricUrl
            = "https://maps.googleapis.com/maps/api/distancematrix/json";
    private static String apiKey = "AIzaSyACujJaDW5GnFSQlioh5Lme-yubu9asAyM";
    private static String locationName="";

    public static Map<String, Double> calculateDistance(List<Evenement> events) {

        Map<String, Double> destinationDistanceMap = new HashMap<String, Double>();
        Location location = LocationManager.getLocationManager().getCurrentLocationSync();
        resolvePlace(new Coord(location.getLatitude(), location.getLongitude()));
        String origin = locationName;
        for (int i = 0; i < events.size(); i++) {
            final Evenement e = events.get(i);
            if (destinationDistanceMap.containsKey(e.getDestination())) {
                continue;
            }
            ConnectionRequest request = new ConnectionRequest(googleDistanceMatricUrl);
            request.setPost(false);

            request.addArgument("origins", origin);
            request.addArgument("destinations", events.get(i).getDestination());
            request.addArgument("language", "fr-FR");
            request.addArgument("key", apiKey);
            request.addResponseListener((evt) -> {
               // System.out.println(new String(request.getResponseData()));
                JSONParser parser = new JSONParser();

                try {
                    Map<String, Object> result = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData())));
                    List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("rows");
                    List<Map<String, Object>> rowZero = (List<Map<String, Object>>) rows.get(0).get("elements");
                    Map<String, Object> rowZeroDistance = (Map<String, Object>) rowZero.get(0);
                    Map<String, Object> rowZeroDistanceResult = (Map<String, Object>) rowZeroDistance.get("distance");
                    //System.out.println("Distance from "+origin+" to "+e.getDestination()+" : "+rowZeroDistanceResult.get("value"));
                    destinationDistanceMap.put(e.getDestination(), ((Double) rowZeroDistanceResult.get("value")) / 1000);
                } catch (IOException exception) {

                }
            });

            NetworkManager.getInstance().addToQueueAndWait(request);

        }
        for (Map.Entry<String, Double> entry : destinationDistanceMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        return destinationDistanceMap;

    }

    public static void resolvePlace(Coord c) {
        ConnectionRequest request = new ConnectionRequest("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + c.getLatitude() + ","
                + c.getLongitude() + "&sensor=true");
        request.addResponseListener((evt) -> {

            String a = (new String(request.getResponseData()));
            // System.out.println(a);
            if (a.indexOf("ZERO_RESULTS") == -1) {
                JSONParser json = new JSONParser();
                try {
                    String ret = "";
                    Map<String, Object> response = json.parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));

                    if (response.get("results") != null) {
                        ArrayList results = (ArrayList) response.get("results");
                        if (results.size() > 0) {
                            ret = (String) ((LinkedHashMap) results.get(1)).get("formatted_address");
                            locationName = ret;

                            // ret = (String) ((LinkedHashMap) results.get(1)).get("long_name");
                            //ret +=","+ (String) ((LinkedHashMap) results.get(2)).get("long_name");
                            //System.out.println(ret);
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        );
        request.setPost(
                true);
        NetworkManager.getInstance()
                .addToQueueAndWait(request);

    }
}
