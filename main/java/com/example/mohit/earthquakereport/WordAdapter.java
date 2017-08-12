package com.example.mohit.earthquakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mohit on 17/5/17.
 */

public class WordAdapter extends ArrayAdapter<Earthquakes> {

    private static final String LOCATION_SEPERATOR="of";

    public WordAdapter(Activity context, ArrayList<Earthquakes> earthquakes){
        super(context,0,earthquakes);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        //get the object located at this position in the list which need to be displayed in
        // listitemview
        Earthquakes currentword=getItem(position);

        //find the textview in the list_item.xml with the id magnitude
        TextView magnitude=(TextView)listItemView.findViewById(R.id.magnitude);
        magnitude.setText(currentword.getMagnitude());

        //find the textview to display place of earthwquake with the id quakePlace id
        TextView place_start=(TextView)listItemView.findViewById(R.id.quakePlace_start);
        TextView place=(TextView)listItemView.findViewById(R.id.quakePlace);
        //get the place location provided by the json object
        String initial_place=currentword.getPlace();
        String newString=getInitialPlace(initial_place);
        String endString=getfinalPlace(initial_place);

        place_start.setText(newString);
        place.setText(endString);

        //find the textview in the list_item.xml with the quakeDate id
        TextView quakeDate=(TextView)listItemView.findViewById(R.id.quakeDate);
        quakeDate.setText(currentword.getQuakeDate());

        //find the textView in the list_item.xml with the quakeTime id
        TextView quakeTime=(TextView)listItemView.findViewById(R.id.quakeTime);
        quakeTime.setText(currentword.getQuakeTime());


        return listItemView;
    }
    //get the initial portion of the place location provided by the json object
    private String getInitialPlace(String initial){
        String newString;
        if(initial.contains(LOCATION_SEPERATOR)){
            int x=initial.indexOf(LOCATION_SEPERATOR);
                newString=initial.substring(0,x+2);
        }
        else
            newString="Near the";
        return newString;
    }
    //get the last portion of the place location provided by the json object
    private String getfinalPlace(String initial){
        String endString;
        if(initial.contains(LOCATION_SEPERATOR)){
            int x=initial.indexOf(LOCATION_SEPERATOR);
            endString=initial.substring(x+2,initial.length());
        }
        else
            endString=initial;
        return endString;
    }

}
