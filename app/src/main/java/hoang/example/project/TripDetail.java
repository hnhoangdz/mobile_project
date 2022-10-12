package hoang.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TripDetail extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_detail);

        Intent intent = getIntent();
        int tripID = intent.getIntExtra("tripID", 0);
        String tripDestination = intent.getStringExtra("tripDestination");
        String tripDate = intent.getStringExtra("tripDate");
        String tripName = intent.getStringExtra("tripName");
        String tripRisk = intent.getStringExtra("tripRisk");
        String tripDesc = intent.getStringExtra("tripDesc");

        // Destination
        TextView destination_profile = findViewById(R.id.destination_profile);
        destination_profile.setText(tripDestination);

        // Date
        EditText date_profile = findViewById(R.id.date_profile);
        date_profile.setText(tripDate);

        // Trip name
        TextView trip_profile = findViewById(R.id.trip_profile);
        trip_profile.setText(tripName);

        // Risk
        TextView riskAssessment_profile = findViewById(R.id.risk_profile);
        riskAssessment_profile.setText(tripRisk);

        // Description
        EditText desc_profile = findViewById(R.id.description_profile);
        desc_profile.setText(tripDesc);

        // Delete a trip
        ImageButton btnDelete = findViewById(R.id.btnDeleteTrip);
        btnDelete.setOnClickListener(view -> {
            DatabaseHelper tripDbHelper = new DatabaseHelper(getApplicationContext());
            tripDbHelper.deleteTrip(tripID);
            Toast.makeText(getApplicationContext(), "Delete " + String.valueOf(tripID) + " successfully!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(TripDetail.this, AllTrips.class);
            startActivity(i);
        });

        // Update trip
        Button btnUpdateTrip = findViewById(R.id.btnUpdateTrip);
        btnUpdateTrip.setOnClickListener(view -> {
            DatabaseHelper tripDbHelper = new DatabaseHelper(getApplicationContext());
            tripDbHelper.updateTrip(tripID, date_profile.getText().toString(), desc_profile.getText().toString());
            Toast.makeText(getApplicationContext(), "Update " + String.valueOf(tripID) + " successfully!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(TripDetail.this, AllTrips.class);
            startActivity(i);
        });

        Button btnAddExpense = findViewById(R.id.btnAddExpense);
        btnAddExpense.setOnClickListener(view -> {
            Intent expense = new Intent(TripDetail.this, AddExpense.class);
            expense.putExtra("tripID", tripID);
            startActivity(expense);
        });

        Button viewAllExpenses = findViewById(R.id.viewAllExpenses);
        viewAllExpenses.setOnClickListener(view ->{
            Intent all_exp = new Intent(TripDetail.this, AllExpenses.class);
            all_exp.putExtra("tripID", tripID);
            startActivity(all_exp);
        });
        Toast.makeText(getApplicationContext(), "TripID: " + String.valueOf(tripID), Toast.LENGTH_LONG).show();
    }
}
