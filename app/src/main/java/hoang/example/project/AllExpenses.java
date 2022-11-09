package hoang.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Entities.Expense;

public class AllExpenses extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_expenses);

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        Intent i = getIntent();
        int tripID = i.getIntExtra("tripID", 0);

        ImageButton btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(view -> {
            Intent intent_home = new Intent(AllExpenses.this, MainActivity.class);
            startActivity(intent_home);
        });

        Toast.makeText(getApplicationContext(), String.valueOf(tripID), Toast.LENGTH_LONG).show();
        ArrayList<Expense> arrayExpenses = dbHelper.getAllExpensesByTrip(String.valueOf(tripID));
        ExpenseListAdapter listExpenseAdapter = new ExpenseListAdapter(AllExpenses.this, arrayExpenses);
        ListView listViewExpense = findViewById(R.id.listViewExpense);
        listViewExpense.setAdapter(listExpenseAdapter);

    }
}
