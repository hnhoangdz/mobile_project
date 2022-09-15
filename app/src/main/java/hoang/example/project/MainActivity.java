package hoang.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private boolean selected_trip = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] trip_names = getResources().getStringArray(R.array.trip_name);

        AutoCompleteTextView autoCompleteTextViewTrip = findViewById(R.id.txtTrip);
        ArrayAdapter<String> adapterTripItems = new ArrayAdapter<String>(this, R.layout.list_trip, trip_names);
        autoCompleteTextViewTrip.setAdapter(adapterTripItems);
        autoCompleteTextViewTrip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected_trip = true;
                autoCompleteTextViewTrip.setError(null);
                String item = adapterView.getItemAtPosition(i).toString();
                if(item.length() == 0){
                    Toast.makeText(getApplicationContext(), "vcl", Toast.LENGTH_LONG).show();
                }
//                Toast.makeText(getApplicationContext(), selected_trip, Toast.LENGTH_LONG).show();
            }
        });

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selected_trip){
                    autoCompleteTextViewTrip.setError("must select one trip");
                }
                else{
                    Toast.makeText(getApplicationContext(), String.valueOf(selected_trip), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}