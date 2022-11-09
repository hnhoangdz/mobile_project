package hoang.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import Entities.Trip;

public class TripListAdapter extends ArrayAdapter<Trip> {

    public TripListAdapter(Context context, ArrayList<Trip> tripArrayList){
        super(context, R.layout.list_trips, tripArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Trip trip = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_trips, parent, false );
        }

        TextView destination = convertView.findViewById(R.id.destination);
        TextView tripName = convertView.findViewById(R.id.tripName);
        TextView date = convertView.findViewById(R.id.date);
        TextView total_expense = convertView.findViewById(R.id.totalExpense);

        destination.setText(trip.getDestination());
        tripName.setText(trip.getTrip_name());
        date.setText(trip.getDate());
        total_expense.setText(trip.getTotalExpense());

        return convertView;
    }
}
