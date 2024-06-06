package com.example.musclepal;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class timePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
    @Override
    public void onTimeSet(TimePicker view, int hour, int minute){//gets clock input and sets alarm
        Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag("1");
        TextView textView = f.getActivity().findViewById(R.id.text_view_countdown);
        textView.setText(hour+":"+minute);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        TimeUpdate(c);
        AlarmStart(c);

        Button cancelAlarm = f.getActivity().findViewById(R.id.cancel_alarm);
        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlarmCancel(f);
            }
        });

    }
    private void TimeUpdate(Calendar c){//updates alarm timer
        Fragment f = ((AppCompatActivity) getContext()).getSupportFragmentManager().findFragmentByTag("1");
        TextView alarmText = f.getActivity().findViewById(R.id.text_view_countdown);
        String time = "Gym Alarm set for: ";
        time+= java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT).format(c.getTime());
        alarmText.setText(time);
    }

    private void AlarmStart (Calendar c){//sets and starts alarm
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }


    public void AlarmCancel(Fragment f){//cancels alarm
        TextView alarmText = f.getActivity().findViewById(R.id.text_view_countdown);
        AlarmManager alarmManager = (AlarmManager) f.getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(f.getActivity(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(f.getContext(), 1, intent, PendingIntent.FLAG_IMMUTABLE );

        alarmManager.cancel(pendingIntent);

        alarmText.setText("Alarm Cancelled");

    }


}
