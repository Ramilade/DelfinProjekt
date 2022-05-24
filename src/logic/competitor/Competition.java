package logic.competitor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Competition {

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String place;
    private int ranking;
    private LocalDate date;
    private double time;


    public Competition(String place, int ranking, String date, double time) {
        this.place = place;
        this.ranking = ranking;
        setDate(date);
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getDate() {
        return date.format(dtf);
    }

    public void setDate(String date) {
        this.date = LocalDate.parse(date,dtf);
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Place: " + place + ";"
            + "Rank: " + ranking + ";"
            + "Date: " + date + ";"
            + "Time: " + time;
    }
}
