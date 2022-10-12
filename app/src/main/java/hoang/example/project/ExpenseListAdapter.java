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

import Entities.Expense;

public class ExpenseListAdapter extends ArrayAdapter<Expense> {
    public ExpenseListAdapter(Context context, ArrayList<Expense> expenseArrayList){
        super(context, R.layout.list_expenses, expenseArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Expense expense = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_expenses, parent, false );
        }

        TextView food = convertView.findViewById(R.id.food);
        TextView comment = convertView.findViewById(R.id.comment);
        TextView amount = convertView.findViewById(R.id.amount);
        TextView date_expense = convertView.findViewById(R.id.date_expense);

        food.setText(expense.getExpense_name());
        comment.setText(expense.getComment());
        amount.setText(expense.getAmount());
        date_expense.setText(expense.getTime());

        return convertView;
    }
}
