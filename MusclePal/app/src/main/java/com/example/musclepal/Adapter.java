package com.example.musclepal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    ArrayList<Exercise> exercises;


    public Adapter(Context ctx, ArrayList<Exercise> exercises){
        this.inflater = LayoutInflater.from(ctx);
        this.exercises = exercises;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = inflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);


    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {//attatches api elements to recyclerview

        holder.ExerciseName.setText(exercises.get(position).getTitle());//get elements for each panel on recycler view
        holder.MusclesUsed.setText(exercises.get(position).getMuscles());
        Picasso.get().load(exercises.get(position).getCoverImage()).into(holder.coverImage);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {//when the element of recycler view is clicked
            @Override
            public void onClick(View view) {

                Fragment f = ((AppCompatActivity) view.getContext()).getSupportFragmentManager().findFragmentByTag("2");
                FragmentTransaction transaction =((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                Fragment d = ((AppCompatActivity) view.getContext()).getSupportFragmentManager().findFragmentByTag("4");
                TextView nameTextview = (TextView) d.getActivity().findViewById(R.id.exerciseNamedes);//set textview of description fragment
                nameTextview.setText(exercises.get(position).getTitle());
                TextView descTextview = (TextView) d.getActivity().findViewById(R.id.exerciseDescription);
                descTextview.setText(exercises.get(position).getDescription());
                transaction.hide(f).show(d).commit();

            }
        });

    }


    @Override
    public int getItemCount() {

        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView coverImage;
        TextView ExerciseName,MusclesUsed;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {//give variables the correct view elements

            super(itemView);

            ExerciseName = itemView.findViewById(R.id.exerciseName);
            MusclesUsed = itemView.findViewById(R.id.MuscleUsed);
            coverImage = itemView.findViewById(R.id.coverImage);
            constraintLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
