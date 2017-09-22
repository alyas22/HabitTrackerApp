package com.example.android.habittrackerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Toshiba on 21/09/17.
 */

public class HabitAdapter extends ArrayAdapter<Habit> {

    private static final String REPEAT_NUMBER = "Repeat: ";

    public HabitAdapter(Context context, ArrayList<Habit> habits) {
        super(context, 0, habits);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Habit habits = getItem(position);

        TextView idText = listItemView.findViewById(R.id.count);
        idText.setText(String.valueOf(habits.getId()));

        TextView nameText = listItemView.findViewById(R.id.name_item);
        nameText.setText(habits.getName());

        String Repeat = habits.getRepeat();
        TextView repeatText = listItemView.findViewById(R.id.repeat_item);
        repeatText.setText(REPEAT_NUMBER + Repeat);

        return listItemView;

    }

}
