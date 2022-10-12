package hoang.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Entities.Trip;

public class AllTrips extends AppCompatActivity {

    private static boolean isSearched = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_trips);

        DatabaseHelper tripDbHelper = new DatabaseHelper(this);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                isSearched = true;
                ArrayList<Trip> arrayTrips = tripDbHelper.searchByTripName(s);
                TripListAdapter listTripAdapter = new TripListAdapter(AllTrips.this, arrayTrips);
                ListView listViewTrips = findViewById(R.id.listViewTrip);
                listViewTrips.setAdapter(listTripAdapter);

                listViewTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(AllTrips.this, TripDetail.class);
                        Trip selectedTrip = arrayTrips.get(i);
                        intent.putExtra("tripID", selectedTrip.getTrip_id());
                        intent.putExtra("tripDestination", selectedTrip.getDestination());
                        intent.putExtra("tripDate", selectedTrip.getDate());
                        intent.putExtra("tripName", selectedTrip.getTrip_name());
                        intent.putExtra("tripRisk", selectedTrip.getRisk_assessment());
                        intent.putExtra("tripDesc", selectedTrip.getDescription());
                        startActivity(intent);
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                isSearched = true;
                ArrayList<Trip> arrayTrips = tripDbHelper.searchByTripName(s);
                TripListAdapter listTripAdapter = new TripListAdapter(AllTrips.this, arrayTrips);
                ListView listViewTrips = findViewById(R.id.listViewTrip);
                listViewTrips.setAdapter(listTripAdapter);

                listViewTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(AllTrips.this, TripDetail.class);
                        Trip selectedTrip = arrayTrips.get(i);
                        intent.putExtra("tripID", selectedTrip.getTrip_id());
                        intent.putExtra("tripDestination", selectedTrip.getDestination());
                        intent.putExtra("tripDate", selectedTrip.getDate());
                        intent.putExtra("tripName", selectedTrip.getTrip_name());
                        intent.putExtra("tripRisk", selectedTrip.getRisk_assessment());
                        intent.putExtra("tripDesc", selectedTrip.getDescription());
                        startActivity(intent);
                    }
                });
                return false;
            }
        });


//            Toast.makeText(AllTrips.this, '3', Toast.LENGTH_LONG).show();
        ArrayList<Trip> arrayTrips = tripDbHelper.getAllTrips();
        TripListAdapter listTripAdapter = new TripListAdapter(AllTrips.this, arrayTrips);
        ListView listViewTrips = findViewById(R.id.listViewTrip);
        listViewTrips.setAdapter(listTripAdapter);

        listViewTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AllTrips.this, TripDetail.class);
                Trip selectedTrip = arrayTrips.get(i);
                intent.putExtra("tripID", selectedTrip.getTrip_id());
                intent.putExtra("tripDestination", selectedTrip.getDestination());
                intent.putExtra("tripDate", selectedTrip.getDate());
                intent.putExtra("tripName", selectedTrip.getTrip_name());
                intent.putExtra("tripRisk", selectedTrip.getRisk_assessment());
                intent.putExtra("tripDesc", selectedTrip.getDescription());
                startActivity(intent);
            }
        });

    }
}
