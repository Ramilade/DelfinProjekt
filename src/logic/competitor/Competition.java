package logic.competitor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Competition {

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String place;
    private int ranking;
    private LocalDate date;
    private double time;


    public Competition(String place, int ranking, LocalDate date, double time) {
        this.place = place;
        this.ranking = ranking;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
