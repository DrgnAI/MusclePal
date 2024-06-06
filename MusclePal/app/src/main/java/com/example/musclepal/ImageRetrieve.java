package com.example.musclepal;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageRetrieve {//class for retrieving images url from below link same way as exercises are retrieved
    private static String JSON_URL3 = "https://wger.de/api/v2/exerciseimage/?format=json&limit=118";
    ArrayList<Image> listImage;

    Context context;
    public ImageRetrieve(Context context){

        this.context = context;
    }
    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(ArrayList<Image> listImage);
    }

    public void getImage(VolleyResponseListener volleyResponseListener){
        listImage = new ArrayList<Image>();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, JSON_URL3,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results3 = response.getJSONArray("results");
                    for (int i = 0; i < results3.length(); i++) {
                        JSONObject imageObject = results3.getJSONObject(i);
                        Image image = new Image();
                        image.setImageURL(imageObject.getString("image").toString());
                        image.setId(imageObject.getString("exercise_base").toString());
                        listImage.add(image);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                volleyResponseListener.onResponse(listImage);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
                volleyResponseListener.onError("Something Wrong");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);


    }};