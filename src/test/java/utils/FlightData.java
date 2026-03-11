package utils;

public class FlightData {
    private String airline;
    private String departureTime;
    private String duration;
    private double price;

    public FlightData(String airline, String departureTime, String duration, double price) {
        this.airline = airline;
        this.departureTime = departureTime;
        this.duration = duration;
        this.price = price;
    }

    public String getAirline() { return airline; }
    public String getDepartureTime() { return departureTime; }
    public String getDuration() { return duration; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return airline + "," + departureTime + "," + duration + "," + price;
    }
}