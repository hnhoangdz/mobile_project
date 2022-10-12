package hoang.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class AddExpense extends AppCompatActivity {

    private String expense_name = "";
    private boolean selected_expense = false;
    private boolean selected_amount = false;
    private boolean selected_time = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);

        Intent intent = getIntent();
        int tripID = intent.getIntExtra("tripID", 0);

        // Process expense_name
        String[] expense_names = getResources().getStringArray(R.array.expense_name);
        AutoCompleteTextView autoCompleteTextViewExpense = findViewById(R.id.txtExpenseName);
        ArrayAdapter<String> adapterExpenseItems = new ArrayAdapter<String>(this, R.layout.drop_down_items, expense_names);
        autoCompleteTextViewExpense.setAdapter(adapterExpenseItems);
        autoCompleteTextViewExpense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if(item.length() > 0){
                    selected_expense = true;
                    expense_name = item;
                    autoCompleteTextViewExpense.setError(null);
                }
            }
        });

        // Process amount
        TextInputEditText txtAmount = findViewById(R.id.txtAmount);

        // Process time
        TextInputEditText txtTime = findViewById(R.id.txtTime);
        Date currentTime = Calendar.getInstance().getTime();
//        final Calendar c = Calendar.getInstance();
//        int year = currentTime.get(Calendar.YEAR);
//        int month = currentTime.get(Calendar.MONTH);
//        int day = currentTime.get(Calendar.DAY_OF_MONTH);
        txtTime.setText(String.valueOf(currentTime));

        // Process comment
        TextInputEditText txtComment = findViewById(R.id.txtComment);

        // Process add expense
        Button btnSaveExpense = findViewById(R.id.btnSaveExpense);
        btnSaveExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selected_expense){
                    autoCompleteTextViewExpense.setError("Expense is required");
                }
                if(TextUtils.isEmpty(txtAmount.getText().toString().trim())){
                    selected_amount = false;
                    txtAmount.setError("Amount is required");
                }
                else{
                    selected_amount = true;
                }
                if(TextUtils.isEmpty(txtTime.getText().toString().trim())){
                    selected_time = false;
                    txtAmount.setError("Time is required");
                }
                else{
                    selected_time = true;
                }
                boolean pass_validation = selected_expense&selected_amount&selected_time;
                if(pass_validation){
                    DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                    dbHelper.insertExpense(String.valueOf(tripID), expense_name, txtAmount.getText().toString().trim(),
                            txtTime.getText().toString().trim(), txtComment.getText().toString().trim());
                    Toast.makeText(getApplicationContext(), String.valueOf(tripID), Toast.LENGTH_LONG).show();
//                    finish();
//                    startActivity(getIntent());
                }else{
                    Toast.makeText(getApplicationContext(), "Save unsuccessfully!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
