package hoang.example.project;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import Entities.Expense;
import Entities.Trip;
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name
    private static final String DATABASE_NAME = "TripManagement";

    // Database instance
    private final SQLiteDatabase database;

    // Trip TABLE
    private static final String TABLE_TRIP = "Trip";
    public static final String TRIP_ID = "trip_id";
    public static final String DESTINATION = "destination";
    public static final String DATE = "date";
    public static final String TRIP_NAME = "trip_name";
    public static final String RISK_ASSESSMENT = "risk_assessment";
    public static final String DESCRIPTION = "description";

    // Create Trip Table
    private static final String TABLE_TRIP_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT)",
            TABLE_TRIP, TRIP_ID, DESTINATION, DATE, TRIP_NAME, RISK_ASSESSMENT, DESCRIPTION);

    // Expense TABLE
    private static final String TABLE_EXPENSE = "Expense";
    public static final String EXPENSE_ID = "expense_id";
    private static final String AMOUNT = "amount";
    private static final String EXPENSE_NAME = "expense_name";
    private static final String TIME = "time";
    private static final String COMMENT = "comment";

    // Create Expense Table
    private static final String TABLE_EXPENSE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT)",
            TABLE_EXPENSE, EXPENSE_ID, TRIP_ID, EXPENSE_NAME, AMOUNT, TIME, COMMENT);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_TRIP_CREATE);
        db.execSQL(TABLE_EXPENSE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);

        Log.v(this.getClass().getName(), TABLE_TRIP + " database upgrade to version " +
                newVersion + " - old data lost");
        onCreate(db);
    }

    // Insert a trip
    public long insertTrip(String destination, String date, String trip_name, String risk_assessment, String description) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(DESTINATION, destination);
        rowValues.put(DATE, date);
        rowValues.put(TRIP_NAME, trip_name);
        rowValues.put(RISK_ASSESSMENT, risk_assessment);
        rowValues.put(DESCRIPTION, description);
        return database.insertOrThrow(TABLE_TRIP, null, rowValues);
    }

    // Insert an expense
    public long insertExpense(String trip_id, String expense_name, String amount, String time, String comment){
        ContentValues rowValues = new ContentValues();
        rowValues.put(TRIP_ID, trip_id);
        rowValues.put(EXPENSE_NAME, expense_name);
        rowValues.put(AMOUNT, amount);
        rowValues.put(TIME, time);
        rowValues.put(COMMENT, comment);
        return database.insertOrThrow(TABLE_EXPENSE, null, rowValues);
    }

    // Get all trips
    public ArrayList<Trip> getAllTrips() {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_TRIP, null);

        ArrayList<Trip> results = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int trip_id = cursor.getInt(0);
            String destination = cursor.getString(1);
            String date = cursor.getString(2);
            String trip_name = cursor.getString(3);
            String risk_assessment = cursor.getString(4);
            String description = cursor.getString(5);
            String totalExpense = sumExpenseByTrip(trip_id);
            if(totalExpense == "0"){
                totalExpense = "Total: 0$";
            }else{
                totalExpense = "Total: " + totalExpense + "$";
            }

            Trip trip = new Trip();
            trip.setTrip_id(trip_id);
            trip.setDestination(destination);
            trip.setDate(date);
            trip.setTrip_name(trip_name);
            trip.setRisk_assessment(risk_assessment);
            trip.setDescription(description);
            trip.setTotalExpense(totalExpense);

            results.add(trip);
            cursor.moveToNext();
        }
        return results;
    }

    // Get all expenses by tripId
    public ArrayList<Expense> getAllExpensesByTrip(String tripId){
        String my_query = "SELECT b.expense_id, b.trip_id, b.expense_name, b.amount, b.time, b.comment FROM " + TABLE_TRIP + " a INNER JOIN " +
                TABLE_EXPENSE + " b ON a.trip_id = b.trip_id WHERE a.trip_id=?";
        Cursor cursor = database.rawQuery(my_query,new String[]{String.valueOf(tripId)});

        ArrayList<Expense> results = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int expense_id = cursor.getInt(0);
            int trip_id = cursor.getInt(1);
            String expense_name = cursor.getString(2);
            String amount = cursor.getString(3);
            String time = cursor.getString(4);
            String comment = cursor.getString(5);

            Expense detail = new Expense();
            detail.setTrip_id(trip_id);
            detail.setExpense_id(expense_id);
            detail.setExpense_name(expense_name);
            detail.setAmount(amount);
            detail.setTime(time);
            detail.setComment(comment);

            results.add(detail);
            cursor.moveToNext();
        }
        return results;
    }

    // Sum expense by id
    public String sumExpenseByTrip(int tripID){
        String my_query = "SELECT b.expense_id, b.trip_id, b.expense_name, b.amount, b.time, b.comment FROM " + TABLE_TRIP + " a INNER JOIN " +
                TABLE_EXPENSE + " b ON a.trip_id = b.trip_id WHERE a.trip_id=?";
        Cursor cursor = database.rawQuery(my_query,new String[]{String.valueOf(tripID)});
        float sum = 0;
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String amount = cursor.getString(3);
            sum += Float.parseFloat(amount);
            cursor.moveToNext();
        }
        return String.valueOf(sum);
    }

    // Delete Trip and reference to expense
    public void deleteTrip(int trip_id){
        database.delete(TABLE_TRIP, "trip_id=?", new String[]{String.valueOf(trip_id)});
        database.delete(TABLE_EXPENSE, "trip_id=?", new String[]{String.valueOf(trip_id)});
    }

    // Update trip (only date and description)
    public void updateTrip(int trip_id, String date, String desc){
        ContentValues values = new ContentValues();
        values.put(DATE, date);
        values.put(DESCRIPTION, desc);
        database.update(TABLE_TRIP, values, "trip_id=?", new String[]{String.valueOf(trip_id)});
    }

    // Delete all trips and all expenses
    public void deleteAllTrips(){
        database.delete(TABLE_TRIP, null, null);
        database.delete(TABLE_EXPENSE, null, null);
    }

    public ArrayList<Trip> searchByTripName(String trip_name_letters){
        if(trip_name_letters.trim() == ""){
            getAllTrips();
        }
        String my_query = "SELECT * FROM " + TABLE_TRIP  +" WHERE trip_name LIKE '%"+trip_name_letters+"%'";
        Cursor cursor = database.rawQuery(my_query, null);
        ArrayList<Trip> results = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int trip_id = cursor.getInt(0);
            String destination = cursor.getString(1);
            String date = cursor.getString(2);
            String trip_name = cursor.getString(3);
            String risk_assessment = cursor.getString(4);
            String description = cursor.getString(5);

            Trip trip = new Trip();
            trip.setTrip_id(trip_id);
            trip.setDestination(destination);
            trip.setDate(date);
            trip.setTrip_name(trip_name);
            trip.setRisk_assessment(risk_assessment);
            trip.setDescription(description);

            results.add(trip);

            cursor.moveToNext();
        }
        return results;
    }
}
