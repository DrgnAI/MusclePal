package com.example.musclepal;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Description extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);




    }
    @Override
    public void onHiddenChanged(boolean hidden) {//show the action share bar
        super.onHiddenChanged(hidden);
        if (hidden) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_description, container, false);
        TextView nameTextview = (TextView) view.findViewById(R.id.exerciseNamedes);
        TextView descTextview = (TextView) view.findViewById(R.id.exerciseDescription);

            nameTextview.setText("Nothing");
            descTextview.setText("Null");

        return view;
    }
}