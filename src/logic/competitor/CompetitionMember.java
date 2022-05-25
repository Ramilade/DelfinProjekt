package logic.competitor;

import logic.Member;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class CompetitionMember extends Member {

    private final ArrayList<Discipline> disciplines;
    private String team;
    private final ArrayList<Competition> competitions;

    public CompetitionMember(int userID, String firstName, String lastName,
                             String birthday, String address, String email,
                             String phoneNumber, String status) {
        super(userID, firstName, lastName, birthday, address, email, phoneNumber, status);
        disciplines = new ArrayList<>();
        competitions = new ArrayList<>();
    }

    public CompetitionMember(int userID, String firstName, String lastName, String birthday, String address, String email, String phoneNumber, String status, String creationDate, String datePaid, String hasPaidInTime) {
        super(userID, firstName, lastName, birthday, address, email, phoneNumber, status, creationDate, datePaid, hasPaidInTime);
        disciplines = new ArrayList<>();
        competitions = new ArrayList<>();
    }


    public ArrayList<Competition> getCompetitions() {
        return competitions;
    }

    public void addCompetitions(ArrayList<Competition> competition) {
        this.competitions.addAll(competition);
    }
    public void addNewComp(){
        Scanner comp = new Scanner(System.in);
        System.out.println("Place of competition: ");
        String place = comp.next();
        System.out.println("Rank achieved: ");
        int rank = comp.nextInt();
        System.out.println("Date of competition/achievement(format: dd/mm/yyyy)");
        String date = comp.next();
        System.out.println("Time achieved (format: mm,ss)");
        double time = comp.nextDouble();
        competitions.add(new Competition(place,rank, date,time));
    }

    public ArrayList<Discipline> getDisciplines() {
        return disciplines;
    }

    public void addDisciplines(ArrayList<Discipline> disciplines) {
        this.disciplines.addAll(disciplines);
    }
    public void addNewDisci(){
        Scanner disci = new Scanner(System.in);
        System.out.println("Disciplin type(Fly/Ryg/Bryst/Crawl)");
        DisciplineType type = DisciplineType.valueOf(disci.next().toUpperCase());
        System.out.println("Record time (format: mm,ss)");
        double record = disci.nextDouble();
        System.out.println("Date record was achieved(format: dd/mm/yyyy) ");
        String date = disci.next();
        disciplines.add(new Discipline(type,record,date));
    }

    public Discipline findDiscipline(DisciplineType type) {
        Discipline discipline = null;
        for (Discipline disc :disciplines) {
            if (disc.getType() == type) {
                discipline = disc;
            }
        }
        return discipline;
    }
}
