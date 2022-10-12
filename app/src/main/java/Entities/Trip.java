package Entities;
import androidx.annotation.NonNull;

public class Trip {
    protected int trip_id;
    protected String trip_name;
    protected String date;
    protected String destination;
    protected String risk_assessment;
    protected String description;

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRisk_assessment() {
        return risk_assessment;
    }

    public void setRisk_assessment(String risk_assessment) {
        this.risk_assessment = risk_assessment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
