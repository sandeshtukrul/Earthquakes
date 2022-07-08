package com.example.earthquakes;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String SPLIT = " of ";

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Earthquake earthquake = getItem(position);

        TextView mag = listView.findViewById(R.id.magnitude);
        String magnitude = magFormat(earthquake.getMag());
        mag.setText(magnitude);

        GradientDrawable gradientDrawable = (GradientDrawable) mag.getBackground();
        int magColor = magnitudeColor(earthquake.getMag());
        gradientDrawable.setColor(magColor);

        String off, main, original = earthquake.getPlace();
        if (original.contains(SPLIT)){
            String[] split = original.split(SPLIT);
            off = split[0] + SPLIT;
            main = split[1];
        } else {
            off = "near the";
            main = original;
        }
        TextView offset = listView.findViewById(R.id.location_offset);
        offset.setText(off);
        TextView place = listView.findViewById(R.id.place);
        place.setText(main);


        Date dateObject = new Date(earthquake.getTime());

        String finalTime = timeFormat(dateObject);
        TextView time = listView.findViewById(R.id.time);
        time.setText(finalTime);

        String finalDate = dateFormat(dateObject);
        TextView date = listView.findViewById(R.id.date);
        date.setText(finalDate);


        return listView;
    }

    private int magnitudeColor(double mag) {
        int decimalMag = (int) Math.floor(mag);
        int magColor;

        switch (decimalMag){
            case 0:
            case 1: magColor = R.color.magnitude1;
            break;
            case 2: magColor = R.color.magnitude2;
            break;
            case 3: magColor = R.color.magnitude3;
                break;
            case 4: magColor = R.color.magnitude4;
                break;
            case 5: magColor = R.color.magnitude5;
                break;
            case 6: magColor = R.color.magnitude6;
                break;
            case 7: magColor = R.color.magnitude7;
                break;
            case 8: magColor = R.color.magnitude8;
                break;
            case 9: magColor = R.color.magnitude9;
                break;
            default: magColor = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magColor);
    }

    private String timeFormat(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm:a");
        return dateFormat.format(dateObject);
    }

    private String dateFormat(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String magFormat(double mag) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(mag);
    }
}
