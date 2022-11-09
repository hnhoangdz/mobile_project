package hoang.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean selected_trip = false;
    private boolean selected_risk = false;
    private boolean selected_destination = false;
    private boolean selected_date = false;
    private String trip_name = "";
    private String risk_assessment = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTranslate = findViewById(R.id.home_btnTranslate);
        btnTranslate.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Translate.class);
            startActivity(intent);
        });

        // Process view all -> render trip details
        Button btnViewAll = findViewById(R.id.btnViewAll);
        btnViewAll.setOnClickListener(view -> {
            Toast.makeText(this, "1", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, AllTrips.class);
            Toast.makeText(this, "2", Toast.LENGTH_LONG).show();
            startActivity(intent);
            Toast.makeText(this, "3", Toast.LENGTH_LONG).show();
        });

        Button btnAddTrips = findViewById(R.id.home_btnAddTrip);
        btnAddTrips.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTrip.class);
            startActivity(intent);
        });
    }
}