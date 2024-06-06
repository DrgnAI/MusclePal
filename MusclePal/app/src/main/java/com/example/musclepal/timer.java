package com.example.musclepal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;


public class timer extends Fragment{//timer fragment setup
    private static final long START_TIME_IN_MILLIS = 600000;
    private TextView alarmText;


    private long timeLeftInMillis = START_TIME_IN_MILLIS;



    public timer() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        Button button = (Button) view.findViewById(R.id.set_time);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//show the time input methods
                DialogFragment TimePicker = new timePicker();
                TimePicker.show(getActivity().getSupportFragmentManager(), "time picker");
            }
        });
        alarmText = view.findViewById(R.id.text_view_countdown);
        Button timePick = view.findViewById(R.id.set_time);
        timePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment pickTime = new timePicker();
                pickTime.show(getActivity().getSupportFragmentManager(), "pick time");
            }
        });

        return view;
    }


}