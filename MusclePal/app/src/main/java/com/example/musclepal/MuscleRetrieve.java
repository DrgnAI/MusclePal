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

public class MuscleRetrieve {//class for retrieving images url from below link same way as exercises are retrieved
    private static String JSON_URL2 = "https://wger.de/api/v2/muscle/?format=json";
    ArrayList<Muscle> listMuscle;

    Context context;
    public MuscleRetrieve(Context context){

        this.context = context;
    }
    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(ArrayList<Muscle> listMuscle);
    }

    public void getMuscle(VolleyResponseListener volleyResponseListener){
        listMuscle = new ArrayList<Muscle>();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, JSON_URL2,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results2 = response.getJSONArray("results");
                    for (int i = 0; i < results2.length(); i++) {
                        JSONObject muscleObject = results2.getJSONObject(i);
                        Muscle muscle = new Muscle();
                        muscle.setMuscleName(muscleObject.getString("name").toString());
                        muscle.setId(muscleObject.getString("id").toString());
                        listMuscle.add(muscle);
//                        Toast.makeText(context, "YEEEEEEEEEEEEE", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                };

                volleyResponseListener.onResponse(listMuscle);

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
