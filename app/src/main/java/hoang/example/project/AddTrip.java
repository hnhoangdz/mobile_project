package hoang.example.project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AddTrip extends AppCompatActivity {
    private boolean selected_trip = false;
    private boolean selected_risk = false;
    private boolean selected_destination = false;
    private boolean selected_date = false;
    private String trip_name = "";
    private String risk_assessment = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_trip);

        // Process view all -> render trip details
        Button btnViewAll = findViewById(R.id.btnViewAll);
        btnViewAll.setOnClickListener(view -> {
            Intent intent = new Intent(AddTrip.this, AllTrips.class);
            startActivity(intent);
        });

        ImageButton btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(view -> {
            Intent intent = new Intent(AddTrip.this, MainActivity.class);
            startActivity(intent);
        });

        // Process destination
        TextInputEditText txtDestination = findViewById(R.id.txtDestination);

        // Process date
        TextInputEditText txtDate = findViewById(R.id.txtDate);
        txtDate.setOnFocusChangeListener((view, b) -> {
            if(b){
                AddTrip.DatePickerFragment newFragment = new AddTrip.DatePickerFragment();
                newFragment.setDateInput(txtDate);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });


        // Process trip_name
        String[] trip_names = getResources().getStringArray(R.array.trip_name);
        AutoCompleteTextView autoCompleteTextViewTrip = findViewById(R.id.txtTrip);
        ArrayAdapter<String> adapterTripItems = new ArrayAdapter<String>(this, R.layout.drop_down_items, trip_names);
        autoCompleteTextViewTrip.setAdapter(adapterTripItems);
        autoCompleteTextViewTrip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if(item.length() > 0){
                    selected_trip = true;
                    autoCompleteTextViewTrip.setError(null);
                    trip_name = item;
                }
            }
        });

        // Process risk assessment
        String[] risk_ass = getResources().getStringArray(R.array.risk_ass);
        AutoCompleteTextView autoCompleteTextViewRisk = findViewById(R.id.txtRiskAssessment);
        ArrayAdapter<String> adapterRisks = new ArrayAdapter<String>(this, R.layout.drop_down_items, risk_ass);
        autoCompleteTextViewRisk.setAdapter(adapterRisks);
        autoCompleteTextViewRisk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if(item.length() > 0){
                    selected_risk = true;
                    autoCompleteTextViewRisk.setError(null);
                    risk_assessment = item;
                }
            }
        });

        // Process description
        TextInputEditText txtDescription = findViewById(R.id.txtDescription);

        // Process button clicked
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selected_trip){
                    autoCompleteTextViewTrip.setError("Trip is required");
                }
                if(!selected_risk){
                    autoCompleteTextViewRisk.setError("Risk is required");
                }
                if(TextUtils.isEmpty(txtDestination.getText().toString().trim())){
                    selected_destination = false;
                    txtDestination.setError("Destination is required");
                }
                else{
                    selected_destination = true;
                }
                if(TextUtils.isEmpty(txtDate.getText().toString().trim())){
                    selected_date = false;
                    txtDate.setError("Date is required");
                }
                else{
                    selected_date = true;
                }
                boolean pass_validation = selected_trip&selected_destination&selected_date&selected_risk;
                if(pass_validation){
                    DatabaseHelper tripDbHelper = new DatabaseHelper(getApplicationContext());
                    tripDbHelper.insertTrip(
                            txtDestination.getText().toString().trim(),
                            txtDate.getText().toString().trim(),
                            trip_name,risk_assessment,
                            txtDescription.getText().toString().trim()
                    );
                    finish();
                    startActivity(getIntent());
                    Toast.makeText(getApplicationContext(), "Save successfully!!!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Save unsuccessfully!!!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        public TextInputEditText getDateInput() {
            return dateInput;
        }

        public void setDateInput(TextInputEditText dateInput) {
            this.dateInput = dateInput;
        }

        TextInputEditText dateInput;

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(requireContext(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            dateInput.setText((String.valueOf(i2 + "/" + i1 + "/" +i)));
        }
    }
}
