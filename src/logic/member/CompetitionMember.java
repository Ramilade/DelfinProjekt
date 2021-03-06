package logic.member;

import java.util.ArrayList;

public class CompetitionMember extends Member {

    private final ArrayList<Discipline> disciplines;
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

    public ArrayList<Discipline> getDisciplines() {
        return disciplines;
    }

    public void addDisciplines(ArrayList<Discipline> disciplines) {
        this.disciplines.addAll(disciplines);
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
