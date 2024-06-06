package com.example.musclepal;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class discover extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Exercise> exercises;
    ArrayList<Muscle> muscles;
    ArrayList<Image> images;


    private static String JSON_URL1 = "https://wger.de/api/v2/exercise/?format=json&language=2&limit=150";//exercise API link
    Adapter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        recyclerView = view.findViewById(R.id.exerciseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        exercises = new ArrayList<>();//arraylist for each item retrieved from api
        muscles = new ArrayList<>();
        images = new ArrayList<>();
        MuscleRetrieve muscleRetrieve = new MuscleRetrieve(getContext());

        muscleRetrieve.getMuscle(new MuscleRetrieve.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), "Something wrong", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(ArrayList<Muscle> listMuscle) {
                muscles = listMuscle;
//            }
//        });
        ImageRetrieve imageRetrieve = new ImageRetrieve(getContext());
        imageRetrieve.getImage(new ImageRetrieve.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), "Something wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<Image> listImage) {
                images = listImage;
//
//            }
//        });





        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, JSON_URL1,null, new Response.Listener<JSONObject>() {//make request from api
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {//loop through all exercises in api
                        String musclestr = new String();
                        String imagestr = new String();
                        String descstr = new String();
                        JSONObject exerciseObject = results.getJSONObject(i);
                        Exercise exercise = new Exercise();
                        exercise.setTitle(exerciseObject.getString("name").toString());

                        descstr = (exerciseObject.getString("description").toString());
                        descstr = descstr.replace("<p>","");//get rid of unneccessary tags
                        descstr = descstr.replace("</p>","");
                        exercise.setDescription(descstr);

                        musclestr = (exerciseObject.getString("muscles").toString());
                        imagestr = (exerciseObject.getString("exercise_base").toString());


                        musclestr = musclestr.replace("[","");
                        musclestr = musclestr.replace("]","");
                        if (musclestr.isEmpty()){
                            exercise.setMuscles("N/A");
                        }else{
                            ArrayList<String> myList = new ArrayList<String>();
                            if (musclestr.contains(",")){
                                myList = new ArrayList<String>(Arrays.asList(musclestr.split(",")));
                            }else{
                                myList = new ArrayList<String>(Arrays.asList(musclestr));
                            }
                            ArrayList<String> myList2 = new ArrayList<String>();

                            try {//try adding muscles to list

                                for(int j=0; j< muscles.size(); j++){
                                myList2.add((muscles.get(j).getId()));
                            }

                            Integer e;
                            e = myList2.indexOf(myList.get(0));
                            exercise.setMuscles(muscles.get(e).getMuscleName());
                            }catch (Exception e){
                                Random rand = new Random();
                                List<String> givenList = Arrays.asList("Anterior deltoid","Biceps brachii", "Gastrocnemius", "Pectoralis major", "Trapezius");
                                String muscle = givenList.get(rand.nextInt(givenList.size()));
                                exercise.setMuscles(muscle);
                            }

                            }
                        ArrayList<String> myList3 = new ArrayList<String>();
                        for (int j=0; j< images.size(); j++){
                            myList3.add((images.get(j).getId()));
                        }

                        Integer f;
                        f = myList3.indexOf(imagestr);

                        try {//get image urls
                            exercise.setCoverImage(images.get(f).getImageURL());
                        }catch (Exception e){//if api has no link for image show this image
                            exercise.setCoverImage("https://whetstonefire.org/wp-content/uploads/2020/06/image-not-available.jpg");
                        }


                        exercises.add(exercise);

                    }
                    adapter = new Adapter(getContext(), exercises);
                    recyclerView.setAdapter(adapter);



                } catch (JSONException e) {

                    e.printStackTrace();
                }



            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(request);
            }
        });
            }
        });


        return view;

    }






}